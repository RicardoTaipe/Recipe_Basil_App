package com.example.recipe_basil_app.ui.carousel.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.recipe_basil_app.R
import com.example.recipe_basil_app.databinding.FragmentRecipeContainerBinding
import com.example.recipe_basil_app.ui.home.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class RecipeContainerFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels ()
    private lateinit var binding: FragmentRecipeContainerBinding
    private val pagerAdapter by lazy { RecipeCarouselAdapter() }
    private lateinit var viewPagerChangeCallback: OnPageChangeCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewPager()
        observeViewModel()
        applyParallaxAnimation()
    }

    private fun setupViewPager() {
        binding.recipesCarousel.adapter = pagerAdapter
        binding.recipesCarousel.offscreenPageLimit = 3
        viewPagerChangeCallback = object :
            OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.selectRecipe(position)
            }
        }
        binding.recipesCarousel.registerOnPageChangeCallback(viewPagerChangeCallback)
    }

    private fun observeViewModel() {
        viewModel.recipeByCategory.observe(viewLifecycleOwner) { recipes ->
            recipes?.let {
                viewLifecycleOwner.lifecycleScope.launch {
                    // Wait for menu drawer animation to end to allow ViewPager refresh.
                    delay(200)
                    pagerAdapter.submitList(it)
                }
            }
        }

        viewModel.selectedCategory.observe(viewLifecycleOwner) {
            viewModel.retrieveRecipesByCategory(it.strCategory)
        }

        // Initial fetch when fragment is created
        viewModel.retrieveRecipesByCategory(null)

    }

    private fun applyParallaxAnimation() {

        binding.recipesCarousel.setPageTransformer { page, position ->
            val nameRecipe: TextView? = page.findViewById(R.id.meal_name)
            val imageRecipe: ImageView? = page.findViewById(R.id.meal_image)
            when {
                position < -1 ->  // Page is off-screen to the left
                    page.alpha = 1f

                position <= 1 -> { // Page is in the center
                    nameRecipe?.translationX = position * page.width
                    imageRecipe?.translationX = -position * page.width
                }

                else -> // Page is off-screen to the right
                    page.alpha = 1f
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.recipesCarousel.unregisterOnPageChangeCallback(viewPagerChangeCallback)
    }
}