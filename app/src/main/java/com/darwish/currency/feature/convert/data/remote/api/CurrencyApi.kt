package com.darwish.currency.feature.convert.data.remote.api

import com.darwish.currency.core.network.ApiUrl
import com.darwish.currency.feature.convert.data.model.ConvertResponse
import com.darwish.currency.feature.convert.data.model.SymbolResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET(ApiUrl.symbols)
    suspend fun getSymbols(): SymbolResponse

    @GET(ApiUrl.convert)
    suspend fun convert(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): ConvertResponse


}