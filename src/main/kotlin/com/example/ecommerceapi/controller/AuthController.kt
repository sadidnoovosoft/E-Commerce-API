package com.example.ecommerceapi.controller

import com.example.ecommerceapi.service.AuthService
import com.example.ecommerceapi.viewmodel.LoginRequestViewModel
import com.example.ecommerceapi.viewmodel.UserInputViewModel
import com.example.ecommerceapi.viewmodel.UserOutputViewModel
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/auth")
class AuthController(val authService: AuthService) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody userInputViewModel: UserInputViewModel): String {
        return authService.register(userInputViewModel)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequestViewModel: LoginRequestViewModel): String {
        return authService.login(loginRequestViewModel)
    }

    @GetMapping("/current")
    fun getLoggedInUser(principal: Principal): UserOutputViewModel {
        return authService.getLoggedInUser(principal)
    }
}