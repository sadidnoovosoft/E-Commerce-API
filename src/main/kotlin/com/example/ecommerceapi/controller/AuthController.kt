package com.example.ecommerceapi.controller

import com.example.ecommerceapi.service.AuthService
import com.example.ecommerceapi.viewmodel.AuthResponseViewModel
import com.example.ecommerceapi.viewmodel.LoginRequestViewModel
import com.example.ecommerceapi.viewmodel.UserInputViewModel
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(val authService: AuthService) {

    @PostMapping("/register")
    fun register(@RequestBody userInputViewModel: UserInputViewModel): AuthResponseViewModel {
        return authService.register(userInputViewModel)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequestViewModel: LoginRequestViewModel): AuthResponseViewModel {
        return authService.login(loginRequestViewModel)
    }
}