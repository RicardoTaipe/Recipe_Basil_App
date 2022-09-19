package com.example.recipe_basil_app.ui.recipes.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_basil_app.databinding.FragmentRecipeBinding
import com.example.recipe_basil_app.util.bindImage

private const val RECIPE_ID = "RECIPE_ID"

class RecipeFragment : Fragment() {

    private lateinit var binding: FragmentRecipeBinding
    private val viewModel: RecipeViewModel by viewModels()
    private var recipeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeId = it.getString(RECIPE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRecipeDetails(recipeId!!)

        viewModel.recipe.observe(viewLifecycleOwner) { recipe ->
            recipe?.strMeal?.let {
                binding.mealName.text = it
            }
            bindImage(binding.mealImage, recipe.strMealThumb)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(id: String?) = RecipeFragment().apply {
            arguments = Bundle().apply {
                putString(RECIPE_ID, id)
            }
        }
    }

}