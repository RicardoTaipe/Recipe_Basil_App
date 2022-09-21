package com.example.recipe_basil_app.ui.carousel.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipe_basil_app.databinding.FragmentRecipeBinding
import com.example.recipe_basil_app.network.response.Meal
import com.example.recipe_basil_app.util.imageUrl


private const val RECIPE = "RECIPE"

class RecipeFragment : Fragment() {

    private lateinit var binding: FragmentRecipeBinding
    private var recipe: Meal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = it.getSerializable(RECIPE) as Meal?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipe?.let {
            binding.mealName.text = it.strMeal ?: ""
            binding.mealImage.imageUrl(it.strMealThumb)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(meal: Meal?) = RecipeFragment().apply {
            arguments = Bundle().apply {
                putSerializable(RECIPE, meal)
            }
        }
    }

}