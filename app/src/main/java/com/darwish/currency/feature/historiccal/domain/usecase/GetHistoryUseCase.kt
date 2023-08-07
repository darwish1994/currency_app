package com.darwish.currency.feature.historiccal.domain.usecase

import com.darwish.currency.core.network.Resource
import com.darwish.currency.feature.historiccal.domain.repo.HistoryRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetHistoryUseCase @Inject constructor(private val historyRepo: HistoryRepo) {

    operator fun invoke(from:String,to: String,lastDays:Int)= flow {
        emit(Resource.Loading())
        emit(historyRepo.getCurrencyHistory(from,to,lastDays))
    }
}