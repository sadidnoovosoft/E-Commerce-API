package com.example.ecommerceapi.viewmodel

import com.example.ecommerceapi.model.User

data class UserInputViewModel(
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
) {
    fun toUser() = User(firstName, lastName, email, password)
}