package com.example.recipe_basil_app.network

import com.example.recipe_basil_app.network.response.Categories
import com.example.recipe_basil_app.network.response.MealByCategory
import com.example.recipe_basil_app.network.response.MealById
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("list.php?c=list")
    suspend fun getAllCategories(): Categories

    @GET("filter.php")
    suspend fun filterByArea(@Query("a") area: String): MealByCategory

    @GET("filter.php")
    suspend fun filterByCategory(@Query("c") category: String): MealByCategory

    @GET("https://www.themealdb.com/api/json/v1/1/lookup.php")
    suspend fun getRecipeById(@Query("i") id: String): MealById
}