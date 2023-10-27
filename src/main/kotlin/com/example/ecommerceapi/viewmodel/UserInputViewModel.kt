package com.example.ecommerceapi.viewmodel

import com.example.ecommerceapi.model.Role
import com.example.ecommerceapi.model.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import org.springframework.security.crypto.password.PasswordEncoder

data class UserInputViewModel(
    val firstName: String,
    val lastName: String,
    @field:Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
    val password: String,
    @field:Email
    val email: String,
    val role: Role,
) {
    fun toUser(passwordEncoder: PasswordEncoder) =
        User(firstName, lastName, email, passwordEncoder.encode(password), role)
}