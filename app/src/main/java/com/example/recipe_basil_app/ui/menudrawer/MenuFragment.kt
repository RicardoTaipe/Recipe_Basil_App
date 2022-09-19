package com.example.recipe_basil_app.ui.menudrawer

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_basil_app.R
import com.example.recipe_basil_app.databinding.FragmentMenuBinding

const val CATEGORY_SELECTED = "category_selected"
const val REQUEST_CATEGORY = "request_category"

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private val categoryAdapter = MenuAdapter()

    companion object {
        fun newInstance() = MenuFragment()
    }

    private val viewModel: MenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMenuBinding.inflate(inflater, container, false)
        binding.categoriesRecyclerview.adapter = categoryAdapter
        val padding = resources.getDimensionPixelSize(R.dimen.item_space)
        binding.categoriesRecyclerview.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = padding
            }
        })
        viewModel.allCategories.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it?.take(if (it.size > 4) 4 else it.size))
        }
        categoryAdapter.itemClickListener = { category ->
            setFragmentResult(REQUEST_CATEGORY, bundleOf(CATEGORY_SELECTED to category.strCategory))
        }

        return binding.root
    }

}
