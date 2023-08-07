package com.darwish.currency.feature.historiccal.data.remote.response

import com.darwish.currency.core.network.BaseResponse
import com.google.gson.annotations.SerializedName

data class CurrencyRateResponse(
    @SerializedName("rates")
    val rates: HashMap<String, Double>
) : BaseResponse()
