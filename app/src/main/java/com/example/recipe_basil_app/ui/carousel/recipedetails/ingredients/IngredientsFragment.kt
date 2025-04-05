package com.example.recipe_basil_app.ui.carousel.recipedetails.ingredients

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.recipe_basil_app.databinding.FragmentIngredientsBinding
import com.example.recipe_basil_app.ui.home.HomeViewModel


class IngredientsFragment : Fragment() {
    private lateinit var binding: FragmentIngredientsBinding
    private val viewModel: HomeViewModel by activityViewModels { HomeViewModel.Factory }
    private val adapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ingredientsList.adapter = adapter
        viewModel.recipe.observe(viewLifecycleOwner) {
            adapter.submitList(it.getIngredients())
        }
    }
}