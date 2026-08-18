package ru.example.exchanger.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.example.exchanger.model.ExchangeEntity

interface ExchangeRepository : JpaRepository<ExchangeEntity, Long> {

    fun findAllByUserId(userId: Long): List<ExchangeEntity>
}