package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Category
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
        sortBy: String?, sortOrder: String?, minPrice: Double?, maxPrice: Double?, category: Category?
    ): ProductsViewModel {
        val productsByCategory = products.filter { category == null || it.category == category }
        val productsInPriceRange = productsByCategory.filter { product ->
            (minPrice == null || product.price >= minPrice) && (maxPrice == null || product.price <= maxPrice)
        }
        val sortedProducts = when (sortBy) {
            "name" -> productsInPriceRange.sortedBy { it.name }
            "price" -> productsInPriceRange.sortedBy { it.price }
            else -> productsInPriceRange.sortedBy { it.id }
        }
        val finalProducts = if (sortOrder == "desc") sortedProducts.reversed() else sortedProducts
        return ProductsViewModel(finalProducts.size, finalProducts)
    }

    fun getProductsByKeywords(keywords: String): ProductsViewModel {
        val searchedProducts = products.filter { product ->
            keywords.split(" ").any { word ->
                product.name.contains(word, ignoreCase = true) || product.description.contains(word, ignoreCase = true)
            }
        }
        return ProductsViewModel(searchedProducts.size, searchedProducts)
    }

    fun getProductDetails(productId: Long): Product {
        return products.first { it.id == productId }
    }

    fun updateProduct(newProduct: Product): Product {
        val oldProduct = products.first { it.id == newProduct.id }
        products.remove(oldProduct)
        products.add(newProduct)
        return newProduct
    }

    fun deleteProduct(productId: Long) {
        val product = products.first { it.id == productId }
        products.remove(product)
    }
}