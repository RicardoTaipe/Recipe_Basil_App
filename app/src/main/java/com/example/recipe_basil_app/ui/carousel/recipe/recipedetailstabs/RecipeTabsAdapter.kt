package com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.directions.DirectionsFragment
import com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.ingredients.IngredientsFragment

class RecipeTabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        val titles = listOf("INGREDIENTS", "DIRECTIONS")
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IngredientsFragment()
            else -> DirectionsFragment()
        }
    }
}