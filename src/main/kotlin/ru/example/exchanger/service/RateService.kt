package ru.example.exchanger.service

import org.springframework.stereotype.Service
import ru.example.exchanger.model.CurrencyRate
import ru.example.exchanger.repository.CurrencyRateRepository

@Service
class RateService(
    private val currencyRateRepository: CurrencyRateRepository
) {

    fun getRate(): CurrencyRate {

        val entity = currencyRateRepository
            .findById(1L)
            .orElseThrow {
                IllegalStateException(
                    "Курс CNY/RUB ещё не загружен"
                )
            }

        return CurrencyRate(
            from = entity.fromCurrency,
            to = entity.toCurrency,
            rate = entity.rate
        )
    }
}