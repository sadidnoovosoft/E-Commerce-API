package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Product
import com.example.ecommerceapi.viewmodel.ProductsViewModel
import org.springframework.stereotype.Service

@Service
class ProductService {
    val products = mutableListOf<Product>()
    private var productIdCounter: Long = 1

    fun addProduct(product: Product): Product {
        products.add(product.copy(id = productIdCounter))
        productIdCounter += 1
        return product
    }

    fun getProducts(
        sortBy: String?, sortOrder: String?, minPrice: Double?, maxPrice: Double?, categoryId: Long?
    ): ProductsViewModel {
        val validProducts = if (categoryId != null) {
            products.filter { it.categoryId == categoryId }
        } else products

        val productsInRange = if (minPrice != null && maxPrice != null) {
            validProducts.filter { it.price in minPrice..maxPrice }
        } else validProducts

        val sortedProducts = when (sortBy) {
            "name" -> productsInRange.sortedBy { it.name }
            "price" -> productsInRange.sortedBy { it.price }
            else -> productsInRange.sortedBy { it.id }
        }
        val finalProducts = if (sortOrder == "desc") sortedProducts.reversed() else sortedProducts
        return ProductsViewModel(finalProducts.size, finalProducts)
    }

    fun getProductsByKeywords(keywords: String): ProductsViewModel {
        val searchedProducts = products.filter { product ->
            keywords.split("+").any { word ->
                product.name.contains(word, ignoreCase = true) ||
                        product.description.contains(word, ignoreCase = true)
            }
        }
        return ProductsViewModel(searchedProducts.size, searchedProducts)
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
}