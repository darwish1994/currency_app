package com.darwish.currency.core.network

import com.darwish.currency.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class ResponseInterceptor @Inject constructor(private val networkHelper: NetworkHelper) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!networkHelper.isNetworkConnected()) throw  NoInternetConnection()

        val url = chain.request().url
            .newBuilder()
//            .addQueryParameter("access_key", BuildConfig.ACCESS_KEY)
            .build()

        val requestBuilder = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("accept", "application/json")
            .addHeader("apikey", BuildConfig.ACCESS_KEY)
            .url(url)

        return chain.proceed(requestBuilder.build())
    }

    inner class NoInternetConnection() : IOException() {

        override val message: String
            get() = " تحقق من إتصال الانترنت "
    }
}
