package ru.example.exchanger.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.example.exchanger.model.UserEntity

interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findByUsername(username: String): UserEntity?

    fun existsByUsername(username: String): Boolean
}