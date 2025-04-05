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
//    private val _categories = MutableLiveData<List<Category>?>()
//    val categories: LiveData<List<Category>?> = _categories
//
//    init {
//        viewModelScope.launch {
//            try {
//                val response = RecipeApiService.recipeApi.getAllCategories()
//                val meals = response.meals.orEmpty()
//                _categories.value = meals.take(minOf(meals.size, 4))
//            } catch (t: Throwable) {
//                Log.d("MenuViewModel", t.toString())
//            }
//        }
//    }
}