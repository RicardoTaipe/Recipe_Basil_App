package com.example.recipe_basil_app.ui.carousel.container

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.recipe_basil_app.network.response.Meal
import com.example.recipe_basil_app.ui.carousel.recipe.RecipeFragment


class RecipePagerAdapter(fa: Fragment, var recipes: List<Meal>?) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = recipes?.size ?: 0

    override fun createFragment(position: Int): Fragment =
        RecipeFragment.newInstance(recipes?.get(position))

}
