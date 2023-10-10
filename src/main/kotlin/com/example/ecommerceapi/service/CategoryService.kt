package com.example.ecommerceapi.service

import com.example.ecommerceapi.model.Category
import org.springframework.stereotype.Service

@Service
class CategoryService {
    val categories = mutableListOf<Category>()
    private var categoryIdCounter: Long = 1

    fun getCategories(sortBy: String?, sortOrder: String?): List<Category> {
        val sortedCategories = when (sortBy) {
            "name" -> categories.sortedBy(Category::name)
            else -> categories.sortedBy(Category::id)
        }
        return if (sortOrder == "desc") sortedCategories.reversed() else sortedCategories
    }

    fun createCategory(category: Category): Category {
        categories.add(category.copy(id = categoryIdCounter))
        categoryIdCounter += 1
        return categories.first { it.id == categoryIdCounter - 1 }
    }
}