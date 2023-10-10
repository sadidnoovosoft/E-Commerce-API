package com.example.ecommerceapi.controller

import com.example.ecommerceapi.model.User
import com.example.ecommerceapi.viewmodel.UserView
import com.example.ecommerceapi.service.AuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class AuthController(
    val authService: AuthService
) {
    @GetMapping("/register")
    fun registerUser(@RequestBody user: User): UserView {
        return authService.registerUser(user)
    }
}