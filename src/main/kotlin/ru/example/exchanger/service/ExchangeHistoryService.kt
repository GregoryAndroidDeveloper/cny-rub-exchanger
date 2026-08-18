package ru.example.exchanger.service

import org.springframework.stereotype.Service
import org.springframework.security.core.context.SecurityContextHolder
import ru.example.exchanger.model.ExchangeHistoryResponse
import ru.example.exchanger.repository.ExchangeRepository
import ru.example.exchanger.repository.UserRepository

@Service
class ExchangeHistoryService(
    private val exchangeRepository: ExchangeRepository,
    private val userRepository: UserRepository
) {

    fun getAll(): List<ExchangeHistoryResponse> {

        val authentication = SecurityContextHolder
            .getContext()
            .authentication
            ?: error("Пользователь не авторизован")

        val username = authentication.name

        val user = userRepository.findByUsername(username)
            ?: error("Пользователь не найден")

        return exchangeRepository
            .findAllByUserId(user.id!!)
            .map { exchange ->

                ExchangeHistoryResponse(
                    id = exchange.id,
                    fromCurrency = exchange.fromCurrency,
                    toCurrency = exchange.toCurrency,
                    amount = exchange.amount,
                    rate = exchange.rate,
                    result = exchange.result,
                    createdAt = exchange.createdAt
                )
            }
    }
}