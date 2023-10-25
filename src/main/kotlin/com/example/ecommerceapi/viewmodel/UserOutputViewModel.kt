package com.example.ecommerceapi.viewmodel

import com.example.ecommerceapi.model.Role

data class UserOutputViewModel(
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role,
)