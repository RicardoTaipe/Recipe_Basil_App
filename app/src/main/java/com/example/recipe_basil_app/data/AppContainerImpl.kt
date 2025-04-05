package com.example.recipe_basil_app.data

import com.example.recipe_basil_app.BuildConfig
import com.example.recipe_basil_app.network.NetworkConstants
import com.example.recipe_basil_app.network.RecipeApi
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AppContainerImpl : AppContainer {

    object NullToEmptyStringAdapter {
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
            .add(NullToEmptyStringAdapter)
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addInterceptor(loggingInterceptor)
            }
        }.build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(NetworkConstants.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    private val retrofitService by lazy { retrofit.create(RecipeApi::class.java) }

    override val recipeRepository: RecipeRepository by lazy {
        NetworkRecipeRepository(retrofitService)
    }

}