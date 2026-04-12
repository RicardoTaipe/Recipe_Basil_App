package com.example.recipe_basil_app.ui.carousel.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.recipe_basil_app.R
import com.example.recipe_basil_app.databinding.FragmentRecipeContainerBinding
import com.example.recipe_basil_app.ui.carousel.container.RecipeCarouselAdapter.Companion.INFINITE_SIZE
import com.example.recipe_basil_app.ui.home.HomeViewModel
import com.example.recipe_basil_app.util.EventObserver


class RecipeContainerFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentRecipeContainerBinding? = null
    private val binding get() = _binding!!
    private val pagerAdapter by lazy { RecipeCarouselAdapter() }
    private lateinit var viewPagerChangeCallback: OnPageChangeCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentRecipeContainerBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewPager()
        observeViewModel()
        applyParallaxAnimation()
    }

    private fun setupViewPager() {
        viewPagerChangeCallback = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                pagerAdapter.currentList.takeIf { it.isNotEmpty() }?.let { list ->
                    viewModel.selectRecipe(position % list.size)
                }
            }
        }

        binding.recipesCarousel.apply {
            adapter = pagerAdapter
            registerOnPageChangeCallback(viewPagerChangeCallback)
            offscreenPageLimit = 2
        }
    }

    private fun observeViewModel() {
        viewModel.recipeByCategory.observe(viewLifecycleOwner) { recipes ->
            recipes?.let { list ->
                pagerAdapter.submitList(list.toList()) {
                    if (list.isNotEmpty()) {
                        val centerStart = (INFINITE_SIZE / 2) - (INFINITE_SIZE / 2 % list.size)
                        binding.recipesCarousel.setCurrentItem(centerStart, false)
                    }
                }
            }
        }
        viewModel.animationFinished.observe(viewLifecycleOwner, EventObserver {
            pagerAdapter.submitList(null)
            viewModel.retrieveRecipesByCategory(viewModel.selectedCategory.value?.strCategory.orEmpty())
        })

        // Initial fetch
        if (viewModel.recipeByCategory.value == null) {
            viewModel.retrieveRecipesByCategory()
        }
    }

    private fun applyParallaxAnimation() {
        binding.recipesCarousel.setPageTransformer { page, position ->
            when {
                position <= 1 -> {
                    page.findViewById<TextView?>(R.id.meal_name)?.translationX =
                        position * page.width
                    page.findViewById<ImageView?>(R.id.meal_image)?.translationX =
                        -position * page.width
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.recipesCarousel.unregisterOnPageChangeCallback(viewPagerChangeCallback)
        super.onDestroyView()
        _binding = null
    }
}