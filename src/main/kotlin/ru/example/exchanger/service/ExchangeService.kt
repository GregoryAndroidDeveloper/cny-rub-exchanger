package ru.example.exchanger.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.example.exchanger.model.ExchangeEntity
import ru.example.exchanger.model.ExchangeRequest
import ru.example.exchanger.model.ExchangeResponse
import ru.example.exchanger.repository.ExchangeRepository
import ru.example.exchanger.repository.UserRepository
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class ExchangeService(
    private val rateService: RateService,
    private val userRepository: UserRepository,
    private val exchangeRepository: ExchangeRepository
) {

    fun calculate(request: ExchangeRequest): ExchangeResponse {

        validate(request)

        val rate = rateService.getRate()

        val result = calculateResult(
            request.amount,
            request.from,
            request.to,
            rate.rate
        )

        return ExchangeResponse(
            from = request.from,
            to = request.to,
            amount = request.amount,
            rate = rate.rate,
            result = result
        )
    }

    fun exchange(request: ExchangeRequest): ExchangeResponse {

        val response = calculate(request)

        val authentication = SecurityContextHolder
            .getContext()
            .authentication
            ?: error("Пользователь не авторизован")

        val username = authentication.name

        val user = userRepository.findByUsername(username)
            ?: error("Пользователь не найден")

        val exchange = ExchangeEntity(
            fromCurrency = response.from,
            toCurrency = response.to,
            amount = response.amount,
            rate = response.rate,
            result = response.result,
            user = user
        )

        exchangeRepository.save(exchange)

        return response
    }

    private fun validate(request: ExchangeRequest) {

        require(request.amount > BigDecimal.ZERO) {
            "Сумма должна быть больше нуля"
        }

        require(request.from != request.to) {
            "Валюты должны отличаться"
        }

        require(
            (request.from == "CNY" && request.to == "RUB") ||
                    (request.from == "RUB" && request.to == "CNY")
        ) {
            "Поддерживается только обмен CNY и RUB"
        }
    }

    private fun calculateResult(
        amount: BigDecimal,
        from: String,
        to: String,
        rate: BigDecimal
    ): BigDecimal {

        val result = when {
            from == "CNY" && to == "RUB" ->
                amount.multiply(rate)

            from == "RUB" && to == "CNY" ->
                amount.divide(rate, 10, RoundingMode.HALF_UP)

            else ->
                error("Неподдерживаемая валютная пара")
        }

        return result.setScale(2, RoundingMode.HALF_UP)
    }
}