package ru.example.exchanger.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.example.exchanger.model.ExchangeHistoryResponse
import ru.example.exchanger.service.ExchangeHistoryService

@RestController
@RequestMapping("/api/exchanges")
class ExchangeHistoryController(
    private val exchangeHistoryService: ExchangeHistoryService
) {

    @GetMapping
    fun getHistory(): List<ExchangeHistoryResponse> {
        return exchangeHistoryService.getAll()
    }
}