package ru.example.exchanger.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.example.exchanger.model.CurrencyRate
import ru.example.exchanger.model.FrankfurterRateResponse
import ru.example.exchanger.service.FrankfurterService
import ru.example.exchanger.service.RateService

@RestController
@RequestMapping("/api/rates")
class RateController(
    private val rateService: RateService,
    private val frankfurterService: FrankfurterService
) {

    @GetMapping
    fun getRate(): CurrencyRate {
        return rateService.getRate()
    }

    @GetMapping("/live")
    fun getLiveRate(): FrankfurterRateResponse {
        return frankfurterService.getCnyToRubRate()
    }
}