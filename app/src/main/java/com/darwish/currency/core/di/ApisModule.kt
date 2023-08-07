package com.darwish.currency.core.di

import com.darwish.currency.feature.convert.data.remote.api.CurrencyApi
import com.darwish.currency.feature.historiccal.data.remote.api.HistoryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApisModule {

    @Provides
    @Singleton
    fun provideCurrencyApiCalls(retrofit: Retrofit): CurrencyApi = retrofit.create(CurrencyApi::class.java)

    @Provides
    @Singleton
    fun provideHistoryApiCalls(retrofit: Retrofit): HistoryApi = retrofit.create(HistoryApi::class.java)

}