package com.darwish.currency.feature.historiccal.domain.model

data class CurrencyRate(
    val date: String,
    val currency: String,
    val rate: Double
)
