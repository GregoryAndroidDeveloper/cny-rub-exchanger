package ru.example.exchanger.security

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.example.exchanger.repository.UserRepository

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {

        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException(
                "Пользователь не найден"
            )

        return User.builder()
            .username(user.username)
            .password(user.password)
            .roles("USER")
            .build()
    }
}

