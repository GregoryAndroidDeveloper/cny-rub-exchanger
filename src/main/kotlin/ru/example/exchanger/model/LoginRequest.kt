package ru.example.exchanger.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(

    @field:NotBlank(message = "Имя пользователя не может быть пустым")
    val username: String,

    @field:NotBlank(message = "Пароль не может быть пустым")
    @field:Size(
        min = 6,
        message = "Пароль должен содержать минимум 6 символов"
    )
    val password: String
)