package com.example.recipe_basil_app.network.response

import com.squareup.moshi.Json

data class Category(
    @Json(name = "strCategory")
    val strCategory: String? = null
)