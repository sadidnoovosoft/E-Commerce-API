package com.example.ecommerceapi.model

import com.example.ecommerceapi.viewmodel.CartItemViewModel
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.Positive

@Entity
class CartItem(
    @Column(name = "quantity", nullable = false)
    @field:Positive
    var quantity: Long,

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0

    fun toCartItemViewModel() = CartItemViewModel(product.id, quantity)
}