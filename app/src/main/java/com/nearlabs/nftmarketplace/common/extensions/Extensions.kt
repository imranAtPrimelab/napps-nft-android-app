package com.nearlabs.nftmarketplace.common.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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