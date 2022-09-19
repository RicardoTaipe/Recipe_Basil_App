package com.example.recipe_basil_app.ui.recipes.container

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.recipe_basil_app.network.response.Meal
import com.example.recipe_basil_app.ui.recipes.recipe.RecipeFragment


class RecipePagerAdapter(fa: FragmentActivity, var recipes: List<Meal>?) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = recipes?.size ?: 0

    override fun createFragment(position: Int): Fragment =
        RecipeFragment.newInstance(recipes?.get(position)?.idMeal)

}
