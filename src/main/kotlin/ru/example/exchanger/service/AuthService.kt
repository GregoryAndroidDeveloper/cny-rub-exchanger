package ru.example.exchanger.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.example.exchanger.model.LoginResponse
import ru.example.exchanger.model.UserEntity
import ru.example.exchanger.repository.UserRepository
import ru.example.exchanger.security.JwtService
import ru.example.exchanger.exception.UnauthorizedException
import ru.example.exchanger.exception.InvalidCredentialsException

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
) {

    fun register(username: String, password: String): UserEntity {

        require(username.isNotBlank()) {
            "Имя пользователя не может быть пустым"
        }

        require(password.length >= 6) {
            "Пароль должен содержать минимум 6 символов"
        }

        require(!userRepository.existsByUsername(username)) {
            "Пользователь уже существует"
        }

        val encodedPassword = passwordEncoder.encode(password)
            ?: error("Не удалось зашифровать пароль")

        val user = UserEntity(
            username = username,
            password = encodedPassword
        )

        return userRepository.save(user)
    }

    fun login(username: String, password: String): LoginResponse {

        val user = userRepository.findByUsername(username)
            ?: throw InvalidCredentialsException()

        val passwordMatches = passwordEncoder.matches(
            password,
            user.password
        )

        if (!passwordMatches) {
            throw InvalidCredentialsException()
        }

        val token = jwtService.generateToken(user.username)

        return LoginResponse(
            id = user.id!!,
            username = user.username,
            token = token
        )
    }
}