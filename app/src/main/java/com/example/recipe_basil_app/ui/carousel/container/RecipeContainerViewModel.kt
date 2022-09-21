package com.example.recipe_basil_app.ui.carousel.container

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_basil_app.network.RecipeApiService
import com.example.recipe_basil_app.network.response.Meal
import com.example.recipe_basil_app.network.response.Recipe
import kotlinx.coroutines.launch

class RecipeContainerViewModel : ViewModel() {

    private val _meals = MutableLiveData<List<Meal>?>()
    val meals: LiveData<List<Meal>?> = _meals

    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe> = _recipe

    fun retrieveRecipesByCategory(item: String?) {
        viewModelScope.launch {
            try {
                val mealByCategory =
                    RecipeApiService.recipeApi.filterByCategory(item ?: "Beef")
                _meals.value = mealByCategory.meals
            } catch (t: Throwable) {
                Log.d("RecipeContainerVModel", t.toString())
            }
        }
    }

    fun itemSelected(position: Int) {
        val id = _meals.value?.get(position)?.idMeal ?: return
        viewModelScope.launch {
            try {
                val recipe = RecipeApiService.recipeApi.getRecipeById(id)
                _recipe.postValue(recipe.meals?.first())
            } catch (t: Throwable) {
                Log.d("RecipeContainerVModel", t.toString())
            }
        }
    }
}
