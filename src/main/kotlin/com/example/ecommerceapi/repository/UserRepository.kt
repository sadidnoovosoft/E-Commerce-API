package com.example.ecommerceapi.repository

import com.example.ecommerceapi.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findUserByEmail(email: String): User
}