package com.example.recipe_basil_app.data

import com.example.recipe_basil_app.network.response.Categories
import com.example.recipe_basil_app.network.response.Category
import com.example.recipe_basil_app.network.response.MealByCategory
import com.example.recipe_basil_app.network.response.MealById

interface RecipeRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getRecipesByCategory(categoryId: String): MealByCategory
    suspend fun getRecipeById(id: String): MealById
}