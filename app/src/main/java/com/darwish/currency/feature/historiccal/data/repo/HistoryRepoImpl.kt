package com.darwish.currency.feature.historiccal.data.repo

import com.darwish.currency.core.extention.addDays
import com.darwish.currency.core.extention.formatToServerDateDefaults
import com.darwish.currency.core.network.NetworkRemoteServiceCall
import com.darwish.currency.core.network.Resource
import com.darwish.currency.feature.historiccal.data.remote.api.HistoryApi
import com.darwish.currency.feature.historiccal.domain.model.CurrencyRate
import com.darwish.currency.feature.historiccal.domain.repo.HistoryRepo
import java.util.Date
import javax.inject.Inject

class HistoryRepoImpl @Inject constructor(private val historyApi: HistoryApi) : HistoryRepo,
    NetworkRemoteServiceCall {
    override suspend fun getCurrencyHistory(
        from: String,
        to: String,
        lastDays: Int
    ): Resource<List<CurrencyRate>> = safeApiCallGeneric {
        historyApi.getTimeSeries(
            from = from,
            to = to,
            endDate = Date().formatToServerDateDefaults(),
            startDate= Date().addDays(-1 * lastDays).formatToServerDateDefaults()
        ).rates.flatMap {rates->
            val date= rates.key
            rates.value.map {
                CurrencyRate(date = date, currency = "$from => ${it.key}", rate = it.value)
            }
        }
    }

    override suspend fun getOtherCurrencyRate(
        base: String,
        popularCurrencies: List<String>
    ): Resource<List<CurrencyRate>> = safeApiCallGeneric {
        historyApi.getCurrencyRate(
            base = base,
            symbols = popularCurrencies.joinToString(",")
        ).rates.flatMap {rates->
            rates.value.map {
                CurrencyRate(date = "", currency = "$base => ${it.key}", rate = it.value)
            }
        }
    }
}