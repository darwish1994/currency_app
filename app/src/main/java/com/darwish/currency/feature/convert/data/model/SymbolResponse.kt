package com.darwish.currency.feature.convert.data.model

import com.darwish.currency.core.network.BaseResponse
import com.google.gson.annotations.SerializedName

data class SymbolResponse(

    @SerializedName("symbols")
    val symbols: Map<String, String>,


    ) : BaseResponse()
