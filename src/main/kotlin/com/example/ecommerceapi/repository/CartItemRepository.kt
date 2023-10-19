package com.example.ecommerceapi.repository

import com.example.ecommerceapi.model.CartItem
import org.springframework.data.jpa.repository.JpaRepository

interface CartItemRepository : JpaRepository<CartItem, Long>