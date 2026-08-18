package ru.example.exchanger.model

import java.math.BigDecimal

data class ExchangeResponse(
    val from: String,
    val to: String,
    val amount: BigDecimal,
    val rate: BigDecimal,
    val result: BigDecimal
)