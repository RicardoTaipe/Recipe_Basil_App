package com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe_basil_app.network.response.Recipe

class RecipeDetailsTabsViewModel : ViewModel() {
    val recipe = MutableLiveData<Recipe>()
}