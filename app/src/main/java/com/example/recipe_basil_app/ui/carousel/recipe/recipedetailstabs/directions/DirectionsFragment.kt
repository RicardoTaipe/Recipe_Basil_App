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
    private val indicatorAdapter = DirectionsAdapter(LAYOUT_INDICATOR)
    private lateinit var callback: OnPageChangeCallback
    private val snapHelper = PagerSnapHelper()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDirectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.recipe.observe(viewLifecycleOwner) {
            it?.let { recipe ->
                adapter.submitList(recipe.getDirections())
                indicatorAdapter.submitList(recipe.getDirections())
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
        binding.pagerIndicator.adapter = indicatorAdapter
        callback = object :
            OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicatorAdapter.selectedPos = position
                indicatorAdapter.notifyDataSetChanged()
                binding.pagerIndicator.smoothScrollToPosition(position)
            }
        }

        binding.directionsList.registerOnPageChangeCallback(callback)
        snapHelper.attachToRecyclerView(binding.pagerIndicator)


        indicatorAdapter.listener = { position, _ ->
            indicatorAdapter.selectedPos = position
            indicatorAdapter.notifyDataSetChanged()
            binding.directionsList.setCurrentItem(position, true)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.directionsList.unregisterOnPageChangeCallback(callback)
    }
}