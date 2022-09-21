package com.example.recipe_basil_app.network.response

import com.squareup.moshi.Json
import java.io.Serializable

data class Meal(
    @Json(name = "idMeal")
    val idMeal: String? = null,
    @Json(name = "strMeal")
    val strMeal: String? = null,
    @Json(name = "strMealThumb")
    val strMealThumb: String? = null
) : Serializable