package com.darwish.currency.core.network

import android.content.Context
import com.darwish.currency.R

sealed class RequestError {
    object NoInternetError : RequestError()
    object ParseJsonError : RequestError()
    data class ResponseError(val msg: String) : RequestError()
    data class HTTPError(val code:Int,val msg: String) : RequestError()

    fun getErrorTitle(context: Context)=context.getString(when(this){
        is RequestError.NoInternetError -> R.string.no_internet_connection_error
        is RequestError.ParseJsonError -> R.string.someting_wrong
        is RequestError.ResponseError -> R.string.bad_request
        is RequestError.HTTPError -> R.string.server_error
    })

    fun getErrorMessage(context: Context)=when(this){
        is RequestError.NoInternetError -> context.getString(R.string.no_internet_connection_msg)
        is RequestError.ParseJsonError -> context.getString( R.string.someting_wrong_msg)
        is RequestError.ResponseError -> msg
        is RequestError.HTTPError -> msg
    }

}

