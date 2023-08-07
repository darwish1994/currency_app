package com.darwish.currency.feature.convert

import com.darwish.currency.core.network.RequestError
import com.darwish.currency.feature.convert.domain.model.Symbol

sealed class ConvertViewState {
    data class IsLoading(val data: Boolean) : ConvertViewState()
    data class UpdateSpinnerWithData(val data: List<Symbol>) : ConvertViewState()
    data class ShowErrorSymbol(val data: RequestError?) : ConvertViewState()
    data class ShowErrorConvert(val data: RequestError?) : ConvertViewState()
    data class ViewConvertResult(val data: Double) : ConvertViewState()


}

