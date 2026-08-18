package ru.example.exchanger.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authHeader = request.getHeader("Authorization")

        if (authHeader == null) {
            filterChain.doFilter(request, response)
            return
        }

        if (!authHeader.startsWith("Bearer ")) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return
        }

        val token = authHeader.substring(7)

        try {

            val username = jwtService.extractUsername(token)

            val authentication =
                UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    emptyList()
                )

            SecurityContextHolder
                .getContext()
                .authentication = authentication

            filterChain.doFilter(request, response)

        } catch (e: Exception) {

            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"

            response.writer.write(
                """{"status":401,"error":"Недействительный или просроченный токен"}"""
            )
        }
    }
}