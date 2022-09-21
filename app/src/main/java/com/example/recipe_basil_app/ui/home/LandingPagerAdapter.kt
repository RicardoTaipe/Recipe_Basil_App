package com.example.recipe_basil_app.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.recipe_basil_app.ui.carousel.container.RecipeContainerFragment
import com.example.recipe_basil_app.ui.menudrawer.MenuFragment

private const val NUM_PAGES = 2
const val MENU_PAGE = 0
const val RECIPES_PAGE = 1

class LandingPagerAdapter(fa: FragmentActivity) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            MENU_PAGE -> {
                MenuFragment()
            }
            else -> RecipeContainerFragment()
        }
    }


}