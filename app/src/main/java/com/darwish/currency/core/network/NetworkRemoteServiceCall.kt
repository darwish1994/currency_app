package com.darwish.currency.core.network


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

interface NetworkRemoteServiceCall {

    suspend fun <T> safeApiCallGeneric(apiCall: suspend () -> T): Resource<T> =
        withContext(Dispatchers.IO) {
            try {
                // invoke suspend service method
                val response = apiCall.invoke()
                val data = (response as? BaseResponse)
                if (data == null)
                    Resource.Success(response)
                else
                    if (response.success)
                        Resource.Success(response)
                    else
                        Resource.Error(RequestError.ResponseError(response.error?.info ?: ""))


            } catch (throwable: Exception) {
                when (throwable) {
                    is ResponseInterceptor.NoInternetConnection -> Resource.Error(RequestError.NoInternetError)
                    is IOException -> Resource.Error(RequestError.ParseJsonError)
                    is HttpException -> Resource.Error(
                        RequestError.HTTPError(
                            throwable.code(),
                            throwable.message()
                        )
                    )

                    else -> Resource.Error(RequestError.ResponseError("SomeTHING Wrong try again"))

                }
            }
        }
}