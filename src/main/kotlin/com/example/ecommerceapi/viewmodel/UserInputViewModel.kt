package com.example.ecommerceapi.viewmodel

import com.example.ecommerceapi.model.Role
import com.example.ecommerceapi.model.User
import org.springframework.security.crypto.password.PasswordEncoder

data class UserInputViewModel(
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    val role: Role,
) {
    fun toUser(passwordEncoder: PasswordEncoder) =
        User(firstName, lastName, email, passwordEncoder.encode(password), role)
}