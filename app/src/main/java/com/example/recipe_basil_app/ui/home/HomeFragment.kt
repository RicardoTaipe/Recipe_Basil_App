package com.example.recipe_basil_app.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_basil_app.R
import com.example.recipe_basil_app.databinding.FragmentHomeBinding
import com.example.recipe_basil_app.ui.menudrawer.CATEGORY_SELECTED
import com.example.recipe_basil_app.ui.recipes.container.PAGE_SELECTED
import com.example.recipe_basil_app.ui.recipes.container.REQUEST_PAGE


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_PAGE) { _, bundle ->
            val page = bundle.getInt(PAGE_SELECTED)
            Log.d("RecipeContainerFragment", page.toString())
            binding.apply {
                landingPager.setCurrentItem(RECIPES_PAGE, true)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.apply {
            landingPager.adapter = LandingPagerAdapter(requireActivity())
            landingPager.setCurrentItem(RECIPES_PAGE, false)
        }
        return binding.root
    }
}