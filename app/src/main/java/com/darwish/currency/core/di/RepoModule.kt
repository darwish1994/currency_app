package com.darwish.currency.core.di

import com.darwish.currency.feature.convert.data.remote.api.CurrencyApi
import com.darwish.currency.feature.convert.data.repo.ConvertRepoImpl
import com.darwish.currency.feature.convert.domain.repo.ConvertRepo
import com.darwish.currency.feature.historiccal.data.remote.api.HistoryApi
import com.darwish.currency.feature.historiccal.data.repo.HistoryRepoImpl
import com.darwish.currency.feature.historiccal.domain.repo.HistoryRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepoModule {
    @Provides
    @ViewModelScoped
    fun provideConvertRepo(currencyApi: CurrencyApi): ConvertRepo = ConvertRepoImpl(currencyApi)

    @Provides
    @ViewModelScoped
    fun provideHistoryRepo(historyApi: HistoryApi): HistoryRepo = HistoryRepoImpl(historyApi)
}