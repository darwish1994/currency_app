package com.darwish.currency.feature.historiccal.domain.usecase

import com.darwish.currency.core.network.Resource
import com.darwish.currency.feature.historiccal.domain.repo.HistoryRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetCurrencyRateUseCase @Inject constructor(private val historyRepo: HistoryRepo) {

    operator fun invoke(base: String) = flow {
        emit(Resource.Loading())
        emit(
            historyRepo.getOtherCurrencyRate(
                base,
                arrayListOf(
                    "USD",
                    "EUR",
                    "EGP",
                    "AED",
                    "SAR",
                    "KWD",
                    "UAH",
                    "MXN",
                    "IRR",
                    "CUP"
                )
            )
        )
    }
}