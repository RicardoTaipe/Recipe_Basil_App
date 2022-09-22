package com.example.recipe_basil_app.ui.menudrawer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.recipe_basil_app.databinding.FragmentMenuBinding

const val CATEGORY_SELECTED = "category_selected"
const val REQUEST_CATEGORY = "request_category"

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private val categoryAdapter = MenuAdapter()

    private val viewModel: MenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoriesRecyclerview.adapter = categoryAdapter

        viewModel.allCategories.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it?.take(if (it.size > 4) 4 else it.size))
        }

        categoryAdapter.itemClickListener = { category, position ->
            categoryAdapter.selectedPos = position
            categoryAdapter.notifyDataSetChanged()
            setFragmentResult(REQUEST_CATEGORY, bundleOf(CATEGORY_SELECTED to category.strCategory))
        }
    }
}
