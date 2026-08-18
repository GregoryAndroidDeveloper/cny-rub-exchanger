package ru.example.exchanger.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class ExchangeHistoryResponse(
    val id: Long,
    val fromCurrency: String,
    val toCurrency: String,
    val amount: BigDecimal,
    val rate: BigDecimal,
    val result: BigDecimal,
    val createdAt: LocalDateTime
)