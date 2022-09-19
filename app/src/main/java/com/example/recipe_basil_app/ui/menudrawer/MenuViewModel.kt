package com.example.recipe_basil_app.ui.menudrawer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_basil_app.network.RecipeApiService
import com.example.recipe_basil_app.network.response.Category
import kotlinx.coroutines.launch

class MenuViewModel : ViewModel() {
    private val _allCategories = MutableLiveData<List<Category>?>()
    val allCategories: LiveData<List<Category>?> = _allCategories


    init {
        viewModelScope.launch {
            try {
                val allCategories = RecipeApiService.recipeApi.getAllCategories()
                _allCategories.value = allCategories.meals
            } catch (t: Throwable) {
                Log.d("MenuViewModel", t.toString())
            }
        }
    }
}