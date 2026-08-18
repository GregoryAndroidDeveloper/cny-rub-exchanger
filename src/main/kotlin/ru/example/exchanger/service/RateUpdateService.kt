package ru.example.exchanger.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.example.exchanger.model.CurrencyRateEntity
import ru.example.exchanger.repository.CurrencyRateRepository

@Service
class RateUpdateService(
    private val frankfurterService: FrankfurterService,
    private val currencyRateRepository: CurrencyRateRepository
) {

    @Scheduled(fixedRate = 30 * 60 * 1000)
    fun updateRate() {

        try {

            val data = frankfurterService.getCnyToRubRate()

            val entity = currencyRateRepository
                .findById(1L)
                .orElse(
                    CurrencyRateEntity(id = 1L)
                )

            entity.fromCurrency = data.base
            entity.toCurrency = data.quote
            entity.rate = data.rate

            currencyRateRepository.save(entity)

            println(
                "Курс обновлён: ${data.base}/${data.quote} = ${data.rate}"
            )

        } catch (e: Exception) {

            println(
                "Не удалось обновить курс: ${e.message}"
            )
        }
    }
}