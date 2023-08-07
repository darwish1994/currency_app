package com.darwish.currency.feature.convert.domain.usecase

import com.darwish.currency.feature.convert.domain.repo.ConvertRepo
import com.darwish.currency.core.network.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(private val convertRepo: ConvertRepo) {

    operator fun invoke(from: String, to: String, amount: Double) = flow {
        emit(Resource.Loading())
        emit(convertRepo.convert(from, to, amount))
    }
}