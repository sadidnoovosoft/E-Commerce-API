package com.example.ecommerceapi.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.Date

@Component
class JwtAuthenticationFilter(val jwtService: JwtService) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("AUTHORIZATION")
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val jwtToken = authHeader.substring(7)
            val claims = jwtService.extractClaims(jwtToken)
            val username = claims.subject
            val authority = claims["authority"].toString()
            if (claims.expiration.after(Date())) {
                val authToken = UsernamePasswordAuthenticationToken(
                    username, null, mutableListOf(SimpleGrantedAuthority(authority))
                )
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        filterChain.doFilter(request, response)
    }
}