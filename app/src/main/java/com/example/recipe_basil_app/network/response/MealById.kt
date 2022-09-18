package com.example.recipe_basil_app.network.response

import com.squareup.moshi.Json

data class MealById(
    @Json(name = "meals")
    val meals: List<Recipe>? = null
)