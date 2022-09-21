package com.example.recipe_basil_app.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object NULL_TO_EMPTY_STRING_ADAPTER {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}

private val moshi by lazy {
    Moshi.Builder()
        .add(NULL_TO_EMPTY_STRING_ADAPTER)
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