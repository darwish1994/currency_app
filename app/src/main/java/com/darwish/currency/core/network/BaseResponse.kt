package com.darwish.currency.core.network

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("success")
    val success: Boolean = false

    @SerializedName("error")
    val error: Error? = null
}