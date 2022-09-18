package com.example.recipe_basil_app.network.response

import com.example.recipe_basil_app.network.response.Category
import com.squareup.moshi.Json

data class Categories(
    @Json(name = "meals")
    val meals: List<Category>? = null
)