package com.darwish.currency.feature.historiccal

import com.darwish.currency.core.network.RequestError

sealed class HistoryViewStatue {
    data class IsLoading(val data: Boolean) : HistoryViewStatue()
    data class ShowError(val data: RequestError?) : HistoryViewStatue()

}