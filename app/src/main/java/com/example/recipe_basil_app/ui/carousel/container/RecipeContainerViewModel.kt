package com.example.recipe_basil_app.ui.carousel.container

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_basil_app.network.RecipeApiService
import com.example.recipe_basil_app.network.response.Meal
import kotlinx.coroutines.launch

class RecipeContainerViewModel : ViewModel() {

    private val _meals = MutableLiveData<List<Meal>?>()
    val meals: LiveData<List<Meal>?> = _meals

    fun retrieveRecipesByCategory(item: String?) {
        viewModelScope.launch {
            try {
                val mealByCategory =
                    RecipeApiService.recipeApi.filterByCategory(item ?: "Seafood")
                _meals.value = mealByCategory.meals
            } catch (t: Throwable) {
                Log.d("MenuViewModel", t.toString())
            }
        }
    }
}
