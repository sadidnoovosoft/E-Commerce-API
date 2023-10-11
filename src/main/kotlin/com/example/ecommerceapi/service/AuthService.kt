package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.User
import com.example.ecommerceapi.viewmodel.UserView
import org.springframework.stereotype.Service

@Service
class AuthService {
    val users = mutableListOf<User>()
    private var userIdCounter: Long = 1

    fun registerUser(user: User): UserView {
        if (users.any { it.email == user.email || it.name == user.name }) {
            throw IllegalArgumentException("User with same name or email already present")
        }
        users.add(user.copy(id = userIdCounter))
        userIdCounter += 1
        return UserView(userIdCounter - 1, user.name, user.email)
    }
}