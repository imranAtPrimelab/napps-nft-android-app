package com.nearlabs.nftmarketplace.common.extensions

sealed class State<out T> {

    data class Success<out T>(val value: T) : State<T>()

    data class GenericError(val throwable: Throwable? = null) : State<Nothing>()

    object None : State<Nothing>()

    object Loading : State<Nothing>()

    object Empty : State<Nothing>()

    data class HttpError(val errorBody: ErrorBody? = null) : State<Nothing>()

    @Throws(Exception::class)
    fun takeValueOrThrow(): T {
        return when (this) {
            is Success -> value
            is GenericError -> throw throwable ?: Throwable()
            else -> throw Throwable("Unknown the result type")
        }
    }
}