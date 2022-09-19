package com.example.recipe_basil_app.ui.recipes.recipe

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipe_basil_app.R

class RecipeDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = RecipeDetailsFragment()
    }

    private lateinit var viewModel: RecipeDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecipeDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}