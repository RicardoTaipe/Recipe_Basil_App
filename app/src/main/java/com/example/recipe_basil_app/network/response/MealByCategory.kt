package com.example.recipe_basil_app.network.response

import com.squareup.moshi.Json

data class MealByCategory(
    @Json(name = "meals")
    val meals: List<Meal>? = null
)