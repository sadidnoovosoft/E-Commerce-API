package com.example.ecommerceapi.model

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    val street: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val pincode: String?,
)