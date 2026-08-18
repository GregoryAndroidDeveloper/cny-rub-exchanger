package ru.example.exchanger.model

import java.math.BigDecimal

data class ExchangeRequest(
    val from: String,
    val to: String,
    val amount: BigDecimal
)