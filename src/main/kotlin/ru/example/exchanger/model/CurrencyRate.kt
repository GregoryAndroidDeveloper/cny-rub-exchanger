package ru.example.exchanger.model

import java.math.BigDecimal

data class CurrencyRate(
    val from: String,
    val to: String,
    val rate: BigDecimal
)