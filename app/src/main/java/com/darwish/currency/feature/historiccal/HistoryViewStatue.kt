package com.darwish.currency.feature.historiccal

import com.darwish.currency.core.network.RequestError

sealed class HistoryViewStatue {
    data class IsLoading(val data: Boolean) : HistoryViewStatue()
    data class ShowErrorHistory(val data: RequestError?) : HistoryViewStatue()
    data class ShowErrorRate(val data: RequestError?) : HistoryViewStatue()

}