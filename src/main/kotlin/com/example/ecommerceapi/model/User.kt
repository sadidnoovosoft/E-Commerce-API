package com.example.ecommerceapi.model

import com.example.ecommerceapi.viewmodel.UserOutputViewModel
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(name = "first_name", nullable = false)
    var firstName: String,

    @Column(name = "last_name", nullable = false)
    var lastName: String,

    @Column(name = "email", unique = true, nullable = false)
    var email: String,

    @Column(name = "password", nullable = false, length = 10)
    var password: String,

    @OneToMany(mappedBy = "user")
    var orders: List<Order> = listOf(),

    @OneToMany
    var cartItems: List<CartItem> = listOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0

    fun toUserOutputViewModel() = UserOutputViewModel(id, firstName, lastName, email)
}
