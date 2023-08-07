package com.darwish.currency.feature.convert.domain.repo

import com.darwish.currency.feature.convert.domain.model.Symbol
import com.darwish.currency.core.network.Resource

interface ConvertRepo {

    suspend fun getSymbols(): Resource<List<Symbol>>

    suspend fun convert(from: String, to: String, amount: Double) :Resource<Double>


}