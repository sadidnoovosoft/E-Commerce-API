package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Product
import com.example.ecommerceapi.viewmodel.ProductListViewModel
import org.springframework.stereotype.Service

@Service
class ProductService(
    val categoryService: CategoryService
) {
    val products = mutableListOf<Product>()
    private var productIdCounter: Long = 1

    fun addProduct(product: Product): Product {
        if (categoryService.categories.none { it.id == product.categoryId }) {
            throw IllegalArgumentException("Invalid categoryId")
        }
        products.add(product.copy(id = productIdCounter))
        productIdCounter += 1
        return product
    }

    fun getProducts(
        sortBy: String?,
        sortOrder: String?,
        minPrice: Double?,
        maxPrice: Double?,
        categoryId: Long?
    ): ProductListViewModel {
        val validProducts =
            if (categoryId != null) {
                products.filter { it.categoryId == categoryId }
            } else products

        val productsInRange =
            if (minPrice != null && maxPrice != null) {
                validProducts.filter { it.price in minPrice..maxPrice }
            } else validProducts

        val sortedProducts = when (sortBy) {
            "name" -> productsInRange.sortedBy { it.name }
            "quantity" -> productsInRange.sortedBy { it.quantity }
            "price" -> productsInRange.sortedBy { it.price }
            else -> productsInRange.sortedBy { it.id }
        }
        val finalProducts = if (sortOrder == "desc") sortedProducts.reversed() else sortedProducts
        return ProductListViewModel(finalProducts.size, finalProducts)
    }

    fun getProductDetails(productId: Long): Product {
        return products.first { it.id == productId }
    }

    fun updateProduct(product: Product): Product {
        val oldProduct = products.first { it.id == product.id }
        products.remove(oldProduct)
        products.add(product)
        return product
    }

    fun deleteProduct(productId: Long) {
        val product = products.first { it.id == productId }
        products.remove(product)
    }

    fun getProductPrice(productId: Long): Double {
        return products.first { it.id == productId }.price
    }
}