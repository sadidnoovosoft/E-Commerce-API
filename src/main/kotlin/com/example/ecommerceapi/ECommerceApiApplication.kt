package com.example.ecommerceapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class ECommerceApiApplication

fun main(args: Array<String>) {
    runApplication<ECommerceApiApplication>(*args)
}
