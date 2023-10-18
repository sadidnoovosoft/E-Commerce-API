package com.example.ecommerceapi.repository

import com.example.ecommerceapi.model.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long>