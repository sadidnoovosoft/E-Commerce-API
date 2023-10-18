package com.example.ecommerceapi.repository

import com.example.ecommerceapi.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>