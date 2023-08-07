package com.darwish.currency.feature.convert


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darwish.currency.core.network.Resource
import com.darwish.currency.feature.convert.domain.usecase.ConvertCurrencyUseCase
import com.darwish.currency.feature.convert.domain.usecase.GetCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConvertViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
) : ViewModel() {

    private val viewStatueLiveData by lazy { MutableSharedFlow<ConvertViewState>() }
    fun getStatueObserver() = viewStatueLiveData.asSharedFlow()
    fun getSymbols() = getCurrencyUseCase().onEach {
        when (it) {
            is Resource.Loading -> viewStatueLiveData.emit(ConvertViewState.IsLoading(true))
            is Resource.Success -> {
                viewStatueLiveData.emit(ConvertViewState.IsLoading(false))
                viewStatueLiveData.emit(
                    ConvertViewState.UpdateSpinnerWithData(
                        it.data ?: arrayListOf()
                    )
                )
            }

            is Resource.Error -> {
                viewStatueLiveData.emit(ConvertViewState.IsLoading(false))
                viewStatueLiveData.emit(ConvertViewState.ShowErrorSymbol(it.exception))

            }

        }

    }.launchIn(viewModelScope)


    private var convertJob: Job? = null
    fun convert(from: String, to: String, amount: Double) {
        convertJob?.cancel()
        convertJob = convertCurrencyUseCase(from, to, amount).onEach {
            when (it) {
                is Resource.Loading -> viewStatueLiveData.emit(ConvertViewState.IsLoading(true))
                is Resource.Success -> {
                    viewStatueLiveData.emit(ConvertViewState.IsLoading(false))
                    viewStatueLiveData.emit(
                        ConvertViewState.ViewConvertResult(it.data ?: 0.0)
                    )
                }

                is Resource.Error -> {
                    viewStatueLiveData.emit(ConvertViewState.IsLoading(false))
                    viewStatueLiveData.emit(ConvertViewState.ShowErrorConvert(it.exception))

                }
            }
        }.launchIn(viewModelScope)
    }

//    fun getCurrencySeriesForMonth(from: String, to: String) = currencyRepo.getCurrencySeriesWithInMonth(from, to).execute(currencySeriesLiveData)


    override fun onCleared() {
        super.onCleared()
        convertJob?.cancel()
    }
}