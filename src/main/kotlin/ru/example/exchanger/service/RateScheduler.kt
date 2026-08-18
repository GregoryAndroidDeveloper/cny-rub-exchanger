package ru.example.exchanger.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RateScheduler(
    private val frankfurterService: FrankfurterService
) {

    @Scheduled(fixedRate = 5 * 60 * 1000)
    fun updateRate() {

        try {

            val rate = frankfurterService.getCnyToRubRate()

            println(
                "Актуальный курс CNY/RUB: ${rate.rate}"
            )

        } catch (e: Exception) {

            println(
                "Не удалось обновить курс: ${e.message}"
            )
        }
    }
}