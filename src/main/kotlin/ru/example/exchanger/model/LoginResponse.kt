package ru.example.exchanger.model

data class LoginResponse(
    val id: Long,
    val username: String,
    val token: String
)