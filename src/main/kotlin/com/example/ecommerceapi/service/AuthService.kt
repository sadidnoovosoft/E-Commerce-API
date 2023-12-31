package com.example.ecommerceapi.service

import com.example.ecommerceapi.security.JwtService
import com.example.ecommerceapi.security.MyUserDetails
import com.example.ecommerceapi.repository.UserRepository
import com.example.ecommerceapi.utils.DuplicateResourceException
import com.example.ecommerceapi.viewmodel.LoginRequestViewModel
import com.example.ecommerceapi.viewmodel.UserInputViewModel
import com.example.ecommerceapi.viewmodel.UserOutputViewModel
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class AuthService(
    val userRepository: UserRepository,
    val jwtService: JwtService,
    val passwordEncoder: PasswordEncoder,
    val authManager: AuthenticationManager
) {
    fun register(userInputViewModel: UserInputViewModel): String {
        if (userRepository.existsByEmail(userInputViewModel.email)) {
            throw DuplicateResourceException("User with email ${userInputViewModel.email} already exists")
        }
        val user = userInputViewModel.toUser(passwordEncoder)
        val userDetails = MyUserDetails(userRepository.save(user))
        return jwtService.generateToken(userDetails)
    }

    fun login(loginRequestViewModel: LoginRequestViewModel): String {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequestViewModel.email,
                loginRequestViewModel.password
            )
        )
        val user = userRepository.findUserByEmail(loginRequestViewModel.email)
        val userDetails = MyUserDetails(user)
        return jwtService.generateToken(userDetails)
    }

    fun getLoggedInUser(principal: Principal): UserOutputViewModel {
        return userRepository.findUserByEmail(principal.name).toUserOutputViewModel()
    }
}