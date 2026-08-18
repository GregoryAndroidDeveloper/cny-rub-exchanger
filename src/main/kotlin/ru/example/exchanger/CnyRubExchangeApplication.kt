package ru.example.exchanger

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class CnyRubExchangeApplication

fun main(args: Array<String>) {
	runApplication<CnyRubExchangeApplication>(*args)
}
