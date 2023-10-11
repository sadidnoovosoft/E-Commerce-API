package com.example.ecommerceapi.model

data class Address(
    val id: Long,
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val pincode: String,
    val userId: Long,
)
