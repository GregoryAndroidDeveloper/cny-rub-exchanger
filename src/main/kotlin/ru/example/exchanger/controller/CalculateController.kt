package ru.example.exchanger.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.example.exchanger.model.ExchangeRequest
import ru.example.exchanger.model.ExchangeResponse
import ru.example.exchanger.service.ExchangeService

@RestController
@RequestMapping("/api/exchange")
class CalculateController(
    private val exchangeService: ExchangeService
) {

    @PostMapping("/calculate")
    fun calculate(
        @RequestBody request: ExchangeRequest
    ): ExchangeResponse {
        return exchangeService.calculate(request)
    }
}