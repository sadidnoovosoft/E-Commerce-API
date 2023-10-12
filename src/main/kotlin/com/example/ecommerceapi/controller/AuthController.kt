package com.example.ecommerceapi.controller

import com.example.ecommerceapi.model.User
import com.example.ecommerceapi.viewmodel.UserViewModel
import com.example.ecommerceapi.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class AuthController(
    val authService: AuthService
) {
    @PostMapping("/register")
    fun registerUser(@RequestBody user: User): UserViewModel {
        return authService.registerUser(user)
    }
}