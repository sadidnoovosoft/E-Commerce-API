package com.example.ecommerceapi.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "orders")
data class Order(
    @Column(name = "quantity")
    val quantity: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: OrderStatus = OrderStatus.CREATED,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "date")
    val date: Date = Date(),

    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0
}

enum class OrderStatus {
    CREATED, SHIPPED, DELIVERED, CANCELLED
}