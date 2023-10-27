package com.example.ecommerceapi.model

import com.example.ecommerceapi.viewmodel.ProductViewModel
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

@Entity
class Product(
    @Column(name = "name", nullable = false)
    @field:NotBlank
    var name: String,

    @Column(name = "price", nullable = false)
    @field:Positive
    var price: Double,

    @Column(name = "description")
    var description: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    var category: Category,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    fun toProductViewModel() = ProductViewModel(id, name, price, description, category)
}

enum class Category {
    ELECTRONICS,
    FASHION,
    FURNITURE
}