package ru.example.exchanger.exception

class InvalidCredentialsException(
    message: String = "Неверное имя пользователя или пароль"
) : RuntimeException(message)