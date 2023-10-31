package com.example.ecommerceapi.repository

import com.example.ecommerceapi.model.Image
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ImageRepository : JpaRepository<Image, Long> {
    fun findByName(name: String): Optional<Image>
}