package com.example.ecommerceapi.viewmodel

import java.time.LocalDateTime

data class ErrorResponseViewModel(
    val message: String,
    val date: LocalDateTime = LocalDateTime.now()
)
