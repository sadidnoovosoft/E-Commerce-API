package com.example.ecommerceapi.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Image(
    @Column(name = "name")
    val name: String?,

    @Column(name = "type")
    val type: String?,

    @Column(name = "file_path")
    val filePath: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}