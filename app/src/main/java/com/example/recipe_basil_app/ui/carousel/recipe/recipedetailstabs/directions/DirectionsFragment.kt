package com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.directions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipe_basil_app.databinding.FragmentDirectionsBinding
import com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.RecipeDetailsTabsViewModel


class DirectionsFragment : Fragment() {
    private lateinit var binding: FragmentDirectionsBinding
    private val viewModel: RecipeDetailsTabsViewModel by viewModels({ requireParentFragment() })
    private val adapter = DirectionsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDirectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.directionsList.adapter = adapter

        viewModel.recipe.observe(viewLifecycleOwner) {
            it?.let { recipe ->
                adapter.submitList(recipe.getDirections())
            }
        }
    }
}