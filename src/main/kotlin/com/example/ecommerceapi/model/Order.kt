package com.example.ecommerceapi.model

import com.example.ecommerceapi.viewmodel.OrderOutputViewModel
import jakarta.persistence.*
import jakarta.validation.constraints.Positive
import java.time.LocalDate

@Entity
@Table(name = "orders")
class Order(
    @Column(name = "quantity")
    @field:Positive
    val quantity: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: OrderStatus = OrderStatus.CREATED,

    @Column(name = "date")
    val date: LocalDate = LocalDate.now(),

    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0

    fun toOrderOutputViewModel(): OrderOutputViewModel {
        return OrderOutputViewModel(
            orderId = id,
            userId = user.id,
            product = product.toProductViewModel(),
            quantity = quantity,
            orderStatus = status,
            totalPrice = quantity * product.price,
            orderDate = date
        )
    }
}

enum class OrderStatus {
    CREATED, SHIPPED, DELIVERED, CANCELLED
}