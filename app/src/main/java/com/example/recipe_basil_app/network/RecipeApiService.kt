package com.example.recipe_basil_app.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


private val moshi by lazy {
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

private val retrofit by lazy {
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(NetworkConstants.BASE_URL)
        .build()
}

object RecipeApiService {
    val recipeApi: RecipeApi by lazy { retrofit.create(RecipeApi::class.java) }
}