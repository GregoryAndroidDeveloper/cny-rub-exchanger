package ru.example.exchanger.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "currency_rates")
class CurrencyRateEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    var fromCurrency: String = "",

    var toCurrency: String = "",

    @Column(precision = 19, scale = 10)
    var rate: BigDecimal = BigDecimal.ZERO
)