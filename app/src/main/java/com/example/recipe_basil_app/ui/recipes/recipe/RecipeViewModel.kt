package com.example.recipe_basil_app.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_basil_app.network.RecipeApiService
import com.example.recipe_basil_app.network.response.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe> = _recipe

    fun getRecipeDetails(id: String) {
        viewModelScope.launch {
            try {
                val recipe = RecipeApiService.recipeApi.getRecipeById(id)
                _recipe.postValue(recipe.meals?.first())
                //Log.d("MenuViewModel", recipe.toString())
            } catch (t: Throwable) {
                Log.d("MenuViewModel", t.toString())
            }
        }
    }
}