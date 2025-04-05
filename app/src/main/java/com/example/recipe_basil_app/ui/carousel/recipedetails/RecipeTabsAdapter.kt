package com.example.recipe_basil_app.ui.carousel.recipedetails

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.recipe_basil_app.R
import com.example.recipe_basil_app.ui.carousel.recipedetails.directions.DirectionsFragment
import com.example.recipe_basil_app.ui.carousel.recipedetails.ingredients.IngredientsFragment

class RecipeTabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        val titles = listOf(R.string.ingredients, R.string.directions)
    }

    override fun getItemCount(): Int = titles.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IngredientsFragment()
            else -> DirectionsFragment()
        }
    }
}