package com.example.recipe_basil_app.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_basil_app.network.RecipeApiService
import com.example.recipe_basil_app.network.response.Category
import com.example.recipe_basil_app.network.response.Meal
import com.example.recipe_basil_app.network.response.Recipe
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<Category>?>()
    val categories: LiveData<List<Category>?> = _categories

    private val _selectedCategory = MutableLiveData<Category>()
    val selectedCategory: LiveData<Category> = _selectedCategory

    private fun selectCategory(category: Category) {
        _selectedCategory.value = category
    }

    private val _recipeByCategory = MutableLiveData<List<Meal>?>()
    val recipeByCategory: LiveData<List<Meal>?> = _recipeByCategory

    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe> = _recipe

    init {
        viewModelScope.launch {
            try {
                val response = RecipeApiService.recipeApi.getAllCategories()
                val meals = response.meals.orEmpty()
                _categories.value = meals.take(minOf(meals.size, 4))
            } catch (t: Throwable) {
                Log.d("MenuViewModel", t.toString())
            }
        }
    }

    fun retrieveRecipesByCategory(item: String?) {
        viewModelScope.launch {
            try {
                val response = RecipeApiService.recipeApi.filterByCategory(item ?: "Beef")
                val meals= response.meals.orEmpty()
                _recipeByCategory.value = meals.take(minOf(meals.size, 5))
            } catch (t: Throwable) {
                Log.d("RecipeContainerVModel", t.toString())
            }
        }
    }

    fun itemSelected(position: Int) {
        val id = _recipeByCategory.value?.get(position)?.idMeal ?: return
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