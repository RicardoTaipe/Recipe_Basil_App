package com.example.recipe_basil_app.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.recipe_basil_app.BasilApplication
import com.example.recipe_basil_app.data.RecipeRepository
import com.example.recipe_basil_app.network.response.Category
import com.example.recipe_basil_app.network.response.Meal
import com.example.recipe_basil_app.network.response.Recipe
import kotlinx.coroutines.launch

class HomeViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _selectedCategory = MutableLiveData<Category>()
    val selectedCategory: LiveData<Category> = _selectedCategory

    private val _recipeByCategory = MutableLiveData<List<Meal>?>()
    val recipeByCategory: LiveData<List<Meal>?> = _recipeByCategory

    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe> = _recipe

    private val _slideOffset = MutableLiveData<Float>()
    val slideOffset: LiveData<Float> = _slideOffset

    init {
        viewModelScope.launch {
            _categories.value = recipeRepository.getCategories()
        }
    }

    fun selectCategory(category: Category) {
        _selectedCategory.value = category
    }

    fun retrieveRecipesByCategory(item: String?) {
        viewModelScope.launch {
            try {
                val response = recipeRepository.getRecipesByCategory(item ?: "Beef")
                val meals = response.meals.orEmpty()
                _recipeByCategory.postValue(meals.take(minOf(meals.size, 5)))
            } catch (t: Throwable) {
                Log.d("RecipeContainerVModel", t.toString())
            }
        }
    }

    fun selectRecipe(position: Int) {
        val id = _recipeByCategory.value?.get(position)?.idMeal ?: return
        viewModelScope.launch {
            try {
                val recipe = recipeRepository.getRecipeById(id)
                _recipe.value = recipe.meals?.first()
            } catch (t: Throwable) {
                Log.d("RecipeContainerVModel", t.toString())
            }
        }
    }

    fun setSlideOffset(offset: Float) {
        _slideOffset.value = offset
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container = (this[APPLICATION_KEY] as BasilApplication).container
                HomeViewModel(container.recipeRepository)
            }
        }
    }
}