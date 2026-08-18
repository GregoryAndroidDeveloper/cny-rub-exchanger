package ru.example.exchanger.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import ru.example.exchanger.model.LoginRequest
import ru.example.exchanger.model.LoginResponse
import ru.example.exchanger.model.RegisterRequest
import ru.example.exchanger.model.RegisterResponse
import ru.example.exchanger.service.AuthService

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody request: RegisterRequest
    ): RegisterResponse {

        authService.register(
            username = request.username,
            password = request.password
        )

        return RegisterResponse(
            message = "Регистрация успешна"
        )
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginRequest
    ): LoginResponse {

        return authService.login(
            username = request.username,
            password = request.password
        )
    }
}