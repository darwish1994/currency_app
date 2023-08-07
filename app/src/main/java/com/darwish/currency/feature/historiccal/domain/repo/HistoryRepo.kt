package com.darwish.currency.feature.historiccal.domain.repo

import com.darwish.currency.feature.historiccal.domain.model.CurrencyRate
import com.darwish.currency.core.network.Resource

interface HistoryRepo {

    suspend fun getCurrencyHistory(from:String,to:String,lastDays:Int):Resource<List<CurrencyRate>>


}