package com.darwish.currency.core.network


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("info")
    val info: String? = null,
    @SerializedName("type")
    val type: String? = null
)