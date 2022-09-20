package com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipe_basil_app.databinding.FragmentIngredientsBinding


class IngredientsFragment : Fragment() {
    private lateinit var binding: FragmentIngredientsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}