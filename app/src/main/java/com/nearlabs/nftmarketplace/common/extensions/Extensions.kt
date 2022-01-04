package com.nearlabs.nftmarketplace.common.extensions

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias ErrorHandler = (exception: Throwable?) -> Unit
typealias SuccessHandler<T> = (value: T) -> Unit
typealias LoadingHandler = () -> Unit
typealias HttpErrorHandler = (error: ErrorBody?) -> Unit
typealias EmptyHandler = () -> Unit


suspend fun <T> safeCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    dataOperation: suspend () -> T
): State<T> {
    return withContext(dispatcher) {
        try {
            State.Success(dataOperation.invoke())
        } catch (throwable: Throwable) {
            State.GenericError(throwable)
        }
    }
}

fun <T> ViewModel.resultFlow(
    firstValue: State<T> = State.None,
    callback: suspend () -> State<T>
): MutableStateFlow<State<T>> = MutableStateFlow(firstValue).apply {
    viewModelScope.launch {
        with(this@resultFlow) {
            tryEmit(State.Loading)
            value = callback.invoke()
        }
    }
}

fun <T> LifecycleCoroutineScope.collect(
    flow: StateFlow<State<T>>,
    errorHandler: ErrorHandler? = null,
    httpErrorHandler: HttpErrorHandler? = null,
    successHandler: SuccessHandler<T>? = null,
    loadingHandler: LoadingHandler? = null
) {
    launch {
        flow.collect {
            when (it) {
                is State.Success<*> -> {
                    successHandler?.invoke(it.takeValueOrThrow())
                }
                is State.GenericError -> {
                    errorHandler?.invoke(it.throwable)
                }
                is State.HttpError -> {
                    httpErrorHandler?.invoke(it.errorBody)
                }
                is State.Loading -> {
                    loadingHandler?.invoke()
                }
                else -> {
                }
            }
        }
    }
}

fun <T> LifecycleOwner.pagingCollect(
    flow: StateFlow<State<T>>,
    errorHandler: ErrorHandler?,
    successHandler: SuccessHandler<T>?,
    loadingHandler: LoadingHandler?,
    emptyHandler: EmptyHandler?
) {
    lifecycleScope.launchWhenStarted {
        flow.collect {
            when (it) {
                is State.Success<*> -> {
                    successHandler?.invoke(it.takeValueOrThrow())
                }
                is State.GenericError -> {
                    errorHandler?.invoke(it.throwable)
                }
                is State.Loading -> {
                    loadingHandler?.invoke()
                }
                is State.Empty -> {
                    emptyHandler?.invoke()
                }
                else -> {
                }
            }
        }
    }
}

fun <T> ViewModel.pagingFlow(
    firstValue: State<List<T>> = State.None,
    isFistPage: () -> Boolean,
    nextPage: () -> Unit,
    callback: suspend () -> State<List<T>>
): MutableStateFlow<State<List<T>>> = MutableStateFlow(firstValue).apply {
    viewModelScope.launch {
        with(this@pagingFlow) {
            if (isFistPage.invoke()) {
                tryEmit(State.Loading)
            }
            when (val result = callback.invoke()) {
                is State.Success -> {
                    if (isFistPage.invoke() && result.takeValueOrThrow().isEmpty()) {
                        tryEmit(State.Empty)
                    } else {
                        nextPage.invoke()
                        tryEmit(result)
                    }
                }
                else -> {
                    tryEmit(result)
                }
            }
        }
    }
}