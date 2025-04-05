package com.example.recipe_basil_app.data

import com.example.recipe_basil_app.network.RecipeApi
import com.example.recipe_basil_app.network.response.Category

class NetworkRecipeRepository(private val retrofitService: RecipeApi) : RecipeRepository {

    override suspend fun getCategories(): List<Category> {
        return try {
            val response = retrofitService.getAllCategories()
            val meals = response.meals.orEmpty()
            return meals.take(minOf(meals.size, 4))
        } catch (t: Throwable) {
            emptyList()
        }
    }

    override suspend fun getRecipesByCategory(categoryId: String) =
        retrofitService.filterByCategory(categoryId)

    override suspend fun getRecipeById(id: String) = retrofitService.getRecipeById(id)

}