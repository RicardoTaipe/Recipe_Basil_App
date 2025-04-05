package com.example.recipe_basil_app.ui.carousel.recipe.recipedetails

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipe_basil_app.R
import com.example.recipe_basil_app.databinding.NewFragmentRecipeDetailsBinding
import com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.RecipeTabsAdapter
import com.example.recipe_basil_app.ui.home.HomeViewModel
import com.example.recipe_basil_app.util.dimenToPx
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class RecipeDetailsFragment : Fragment(), TabLayout.OnTabSelectedListener {
    private lateinit var binding: NewFragmentRecipeDetailsBinding
    private lateinit var adapter: RecipeTabsAdapter
    private lateinit var sheetBehavior: BottomSheetBehavior<MotionLayout>
    private lateinit var tabsSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val recipeViewModel: HomeViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewFragmentRecipeDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = recipeViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecipeTabsAdapter(this)
        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = RecipeTabsAdapter.titles[position]
        }.attach()

        tabsSheetBehavior = BottomSheetBehavior.from(binding.recipeTabs)

        val backCallback =
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, false) {
                tabsSheetBehavior.state = STATE_COLLAPSED
            }

        tabsSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                backCallback.isEnabled = newState == STATE_EXPANDED
                tabsSheetBehavior.isDraggable = newState == STATE_EXPANDED
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        binding.tabLayout.addOnTabSelectedListener(this)

        recipeViewModel.recipe.observe(viewLifecycleOwner) {
            it?.let { recipe ->
                Log.d("details fragment", recipe.toString())
            }
        }
        setPeekHeight()
        animateWhenBottomSheetIsDragged()
    }

    private fun setPeekHeight() {
        sheetBehavior = BottomSheetBehavior.from(binding.recipeDetailsContent.recipeBottomSheet)
        val carouselImageHeight = requireContext().dimenToPx(R.dimen.carousel_image_height)
        val carouselTopSpacing = requireContext().dimenToPx(R.dimen.carousel_top_spacing)
        sheetBehavior.peekHeight =
            (Resources.getSystem().displayMetrics.heightPixels - carouselTopSpacing - carouselImageHeight).toInt()
    }

    private fun animateWhenBottomSheetIsDragged() {
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //backCallback.isEnabled = newState == BottomSheetBehavior.STATE_EXPANDED
                binding.recipeDetailsContent.recipeBottomSheet.alpha = if(newState == BottomSheetBehavior.STATE_COLLAPSED ) 0f else 1f
                //TODO fix how to disable this!! this was working
                //before using Fragmetn receipe carousel container
                //binding.carouselContainer.recipesCarousel.isUserInputEnabled = newState != BottomSheetBehavior.STATE_EXPANDED
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //binding.recipeBottomSheet.alpha = slideOffset
                binding.recipeDetailsContent.recipeBottomSheet.progress = slideOffset
                //binding.recipesCarousel.alpha = 1f - slideOffset
                //binding.recipeTitle.alpha = 1f * slideOffset
                //binding.carouselContainer.alpha = 1f - slideOffset
                //binding.titleApp.translationY = (binding.titleApp.height / 2) * -slideOffset
            }

        })
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tabsSheetBehavior.state = STATE_EXPANDED
    }
    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {
        tabsSheetBehavior.state = STATE_EXPANDED
    }

}