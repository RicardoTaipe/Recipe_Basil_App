package com.example.recipe_basil_app.data

import com.example.recipe_basil_app.network.RecipeApi
import com.example.recipe_basil_app.network.response.Category
import com.example.recipe_basil_app.network.response.Meal
import com.example.recipe_basil_app.network.response.Recipe

class NetworkRecipeRepository(private val retrofitService: RecipeApi) : RecipeRepository {

    override suspend fun getCategories(): List<Category> {
        return try {
            val response = retrofitService.getAllCategories()
            val meals = response.meals.orEmpty()
            meals.take(minOf(meals.size, 6))
        } catch (t: Throwable) {
            emptyList()
        }
    }

    override suspend fun getRecipesByCategory(categoryId: String): List<Meal> {
        return try {
            val response = retrofitService.filterByCategory(categoryId)
            val meals = response.meals.orEmpty()
            meals.take(minOf(meals.size, 5))
        } catch (t: Throwable) {
            emptyList()
        }
    }


    override suspend fun getRecipeById(id: String): Recipe? {
        return try {
            val recipe = retrofitService.getRecipeById(id)
            recipe.meals?.first()
        } catch (t: Throwable) {
            null
        }
    }

}