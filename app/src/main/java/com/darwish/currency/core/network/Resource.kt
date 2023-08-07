package com.darwish.currency.core.network

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val exception: RequestError? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(exception: RequestError? = null) : Resource<T>(exception = exception)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}