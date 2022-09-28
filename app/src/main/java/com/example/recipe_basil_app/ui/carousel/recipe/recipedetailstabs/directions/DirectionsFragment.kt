package com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.directions


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.recipe_basil_app.databinding.FragmentDirectionsBinding
import com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.RecipeDetailsTabsViewModel


class DirectionsFragment : Fragment() {
    private lateinit var binding: FragmentDirectionsBinding
    private val viewModel: RecipeDetailsTabsViewModel by viewModels({ requireParentFragment() })
    private val adapter = DirectionsAdapter(LAYOUT_DIRECTION)
    private lateinit var callback: OnPageChangeCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDirectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.recipe.observe(viewLifecycleOwner) {
            it?.let { recipe ->
                adapter.submitList(recipe.getDirections())
                binding.pagerIndicator.initWith(binding.directionsList)
            }
        }

        binding.directionsList.adapter = adapter
        binding.directionsList.setPageTransformer { page, position ->
            if (position <= 0) {
                page.alpha = 1f + position
            }
            if (position <= 1) {
                page.alpha = 1f - position
            }
        }

        callback = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.pagerIndicator.moveTo(position)
            }
        }

        binding.directionsList.registerOnPageChangeCallback(callback)

        binding.pagerIndicator.indicatorClickListener = {
            binding.pagerIndicator.moveTo(it)
            binding.directionsList.setCurrentItem(it, true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.directionsList.unregisterOnPageChangeCallback(callback)
    }
}