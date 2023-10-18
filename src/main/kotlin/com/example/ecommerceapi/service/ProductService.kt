package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Category
import com.example.ecommerceapi.model.Product
import com.example.ecommerceapi.repository.ProductRepository
import com.example.ecommerceapi.viewmodel.ProductViewModel
import com.example.ecommerceapi.viewmodel.ProductsViewModel
import org.springframework.stereotype.Service

@Service
class ProductService(val productRepository: ProductRepository) {
    fun convertToProductViewModel(product: Product): ProductViewModel {
        return ProductViewModel(
            product.id, product.name, product.price, product.description, product.category
        )
    }

    fun addProduct(product: Product): ProductViewModel {
        val savedProduct = productRepository.save(product)
        return convertToProductViewModel(savedProduct)
    }

    fun getProducts(
        sortBy: String?, sortOrder: String?, minPrice: Double?, maxPrice: Double?, category: Category?
    ): ProductsViewModel {
        val products = productRepository.findAll()
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
        val products = productRepository.findAll()
        val searchedProducts = products.filter { product ->
            keywords.split(" ").any { word ->
                product.name.contains(word, ignoreCase = true) || product.description.contains(word, ignoreCase = true)
            }
        }
        return ProductsViewModel(searchedProducts.size, searchedProducts)
    }

    fun getProductById(productId: Long): ProductViewModel {
        val product = productRepository.findById(productId).get()
        return convertToProductViewModel(product)
    }

    fun updateProduct(newProduct: Product, productId: Long): ProductViewModel {
        val existingProduct = productRepository.findById(productId).get()
        existingProduct.name = newProduct.name
        existingProduct.price = newProduct.price
        existingProduct.description = newProduct.description
        existingProduct.category = newProduct.category

        val updatedProduct = productRepository.save(existingProduct)
        return convertToProductViewModel(updatedProduct)
    }

    fun deleteProduct(productId: Long) {
        productRepository.deleteById(productId)
    }
}