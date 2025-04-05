package com.example.recipe_basil_app.ui.menudrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipe_basil_app.databinding.FragmentMenuBinding
import com.example.recipe_basil_app.ui.home.HomeViewModel

const val CATEGORY_SELECTED = "category_selected"
const val REQUEST_CATEGORY = "request_category"

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private val categoryAdapter by lazy { MenuAdapter() }

    private val viewModel: HomeViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoriesRecyclerview.adapter = categoryAdapter

        categoryAdapter.itemClickListener = { category, position ->
            categoryAdapter.selectedPos = position
            categoryAdapter.notifyDataSetChanged()

            viewModel.selectCategory(category)
        }

        viewModel.categories.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
        }
    }
}
