package com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipe_basil_app.databinding.FragmentIngredientsBinding
import com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.RecipeDetailsTabsViewModel


class IngredientsFragment : Fragment() {
    private lateinit var binding: FragmentIngredientsBinding
    private val viewModel: RecipeDetailsTabsViewModel by viewModels({ requireParentFragment() })
    private val adapter = IngredientsAdapter()

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
            it?.let { recipe ->
                adapter.submitList(recipe.getIngredients())
            }
        }
    }

}