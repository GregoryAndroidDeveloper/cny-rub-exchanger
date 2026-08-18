package ru.example.exchanger.service

import org.springframework.stereotype.Service
import tools.jackson.databind.ObjectMapper
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import ru.example.exchanger.model.FrankfurterRateResponse

@Service
class FrankfurterService(
    private val objectMapper: ObjectMapper
) {

    private val httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .build()

    fun getCnyToRubRate(): FrankfurterRateResponse {

        val request = HttpRequest.newBuilder()
            .uri(
                URI.create(
                    "https://api.frankfurter.dev/v2/rate/CNY/RUB"
                )
            )
            .GET()
            .build()

        val response = httpClient.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        )

        if (response.statusCode() != 200) {
            throw IllegalStateException(
                "Frankfurter вернул HTTP ${response.statusCode()}"
            )
        }

        return objectMapper.readValue(
            response.body(),
            FrankfurterRateResponse::class.java
        )
    }
}