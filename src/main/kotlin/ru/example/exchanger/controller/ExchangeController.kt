package ru.example.exchanger.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.example.exchanger.model.ExchangeRequest
import ru.example.exchanger.model.ExchangeResponse
import ru.example.exchanger.service.ExchangeService
import org.springframework.web.bind.annotation.GetMapping
import ru.example.exchanger.model.ExchangeHistoryResponse
import ru.example.exchanger.service.ExchangeHistoryService

@RestController
@RequestMapping("/api/exchange")
class ExchangeController(
    private val exchangeService: ExchangeService,
    private val exchangeHistoryService: ExchangeHistoryService
) {

    @PostMapping
    fun exchange(
        @RequestBody request: ExchangeRequest
    ): ExchangeResponse {
        return exchangeService.exchange(request)
    }

    @GetMapping
    fun getHistory(): List<ExchangeHistoryResponse> {
        return exchangeHistoryService.getAll()
    }
}