package ru.example.exchanger.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "exchanges")
class ExchangeEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(precision = 38, scale = 2)
    var amount: BigDecimal = BigDecimal.ZERO,

    @Column(precision = 19, scale = 10)
    var rate: BigDecimal = BigDecimal.ZERO,

    @Column(precision = 19, scale = 2)
    var result: BigDecimal = BigDecimal.ZERO,

    var fromCurrency: String = "",

    var toCurrency: String = "",

    var createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null
)