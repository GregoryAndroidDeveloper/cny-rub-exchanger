package ru.example.exchanger.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.example.exchanger.model.CurrencyRateEntity

interface CurrencyRateRepository : JpaRepository<CurrencyRateEntity, Long>