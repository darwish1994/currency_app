package com.darwish.currency.feature.historiccal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darwish.currency.core.network.Resource
import com.darwish.currency.feature.historiccal.domain.usecase.GetHistoryUseCase
import com.darwish.currency.feature.historiccal.list.CurrencySeriesAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val historyUseCase: GetHistoryUseCase) :
    ViewModel() {
    val currencyRateAdapter by lazy { CurrencySeriesAdapter() }

    // view state
    private val viewStateLiveData by lazy { MutableSharedFlow<HistoryViewStatue>() }
    fun getViewState() = viewStateLiveData.asSharedFlow()

    private var historyJob: Job? = null
    fun getHistory(from: String, to: String) {
        historyJob?.cancel()
        // get history of lass 3 days
        historyJob = historyUseCase(from, to, 3).onEach {
            when (it) {
                is Resource.Loading -> viewStateLiveData.emit(HistoryViewStatue.IsLoading(true))
                is Resource.Success -> {
                    viewStateLiveData.emit(HistoryViewStatue.IsLoading(false))
                    currencyRateAdapter.updateData(it.data ?: arrayListOf())
                }

                is Resource.Error -> {
                    viewStateLiveData.emit(HistoryViewStatue.IsLoading(false))
                    viewStateLiveData.emit(HistoryViewStatue.ShowError(it.exception))
                }
            }

        }.launchIn(viewModelScope)
    }


    override fun onCleared() {
        super.onCleared()
        historyJob?.cancel()
    }

}