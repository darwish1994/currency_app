package com.darwish.currency.feature.historiccal.data.remote.api

import com.darwish.currency.core.network.ApiUrl
import com.darwish.currency.feature.historiccal.data.remote.response.CurrencySeriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HistoryApi {

    @GET(ApiUrl.history)
    suspend fun getTimeSeries(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("base") from: String,
        @Query("symbols") to: String
    ): CurrencySeriesResponse

    @GET(ApiUrl.rate)
    suspend fun getCurrencyRate(
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): CurrencySeriesResponse
}