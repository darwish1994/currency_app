package com.darwish.currency.feature.convert.data.repo

import com.darwish.currency.feature.convert.data.remote.api.CurrencyApi
import com.darwish.currency.feature.convert.domain.model.Symbol
import com.darwish.currency.feature.convert.domain.repo.ConvertRepo
import com.darwish.currency.core.network.NetworkRemoteServiceCall
import com.darwish.currency.core.network.Resource
import javax.inject.Inject

class ConvertRepoImpl @Inject constructor(private val currencyApi: CurrencyApi) : ConvertRepo,
    NetworkRemoteServiceCall {
    override suspend fun getSymbols(): Resource<List<Symbol>> = safeApiCallGeneric {
        val response = currencyApi.getSymbols()
        return@safeApiCallGeneric response.symbols.map {
            Symbol(
                it.key,
                it.value
            )
        }
    }

    override suspend fun convert(from: String, to: String, amount: Double): Resource<Double> =
        safeApiCallGeneric {
            currencyApi.convert(from, to, amount).result
        }
}