package com.example.ecommerceapi.service

import com.example.ecommerceapi.config.JwtService
import com.example.ecommerceapi.config.MyUserDetails
import com.example.ecommerceapi.repository.UserRepository
import com.example.ecommerceapi.viewmodel.AuthResponseViewModel
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
    fun register(userInputViewModel: UserInputViewModel): AuthResponseViewModel {
        val user = userInputViewModel.toUser(passwordEncoder)
        val userDetails = MyUserDetails(userRepository.save(user))
        return AuthResponseViewModel(jwtService.generateToken(userDetails))
    }

    fun login(loginRequestViewModel: LoginRequestViewModel): AuthResponseViewModel {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequestViewModel.email,
                loginRequestViewModel.password
            )
        )
        val user = userRepository.findUserByEmail(loginRequestViewModel.email)
        val userDetails = MyUserDetails(user)
        return AuthResponseViewModel(jwtService.generateToken(userDetails))
    }

    fun getLoggedInUser(principal: Principal): UserOutputViewModel {
        return userRepository.findUserByEmail(principal.name).toUserOutputViewModel()
    }
}