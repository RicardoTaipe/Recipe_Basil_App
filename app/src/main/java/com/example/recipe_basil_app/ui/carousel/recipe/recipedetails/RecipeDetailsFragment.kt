package com.example.recipe_basil_app.ui.carousel.recipe.recipedetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipe_basil_app.R
import com.example.recipe_basil_app.databinding.FragmentRecipeDetailsBinding
import com.example.recipe_basil_app.ui.carousel.container.RecipeContainerViewModel
import com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.RecipeDetailsTabFragment


class RecipeDetailsFragment : Fragment() {
    private lateinit var binding: FragmentRecipeDetailsBinding
    private val viewModel: RecipeContainerViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.recipe.observe(viewLifecycleOwner) {
            it?.let { recipe ->
                Log.d("RecipeDetailsFragment", recipe.toString())
                (childFragmentManager.findFragmentById(R.id.tabs_bottom_sheet)
                        as? RecipeDetailsTabFragment)?.passRecipe(recipe)
            }
        }
    }

}