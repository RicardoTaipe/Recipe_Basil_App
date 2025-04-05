package com.example.recipe_basil_app.ui.carousel.recipedetails

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.recipe_basil_app.R
import com.example.recipe_basil_app.databinding.NewFragmentRecipeDetailsBinding
import com.example.recipe_basil_app.ui.home.HomeViewModel
import com.example.recipe_basil_app.util.dimenToPx
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class RecipeDetailsFragment : Fragment() {
    private lateinit var binding: NewFragmentRecipeDetailsBinding
    private lateinit var adapter: RecipeTabsAdapter
    private lateinit var sheetBehavior: BottomSheetBehavior<MotionLayout>
    private lateinit var tabsSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var tabsBackPressedCallback: OnBackPressedCallback

    private val recipeViewModel: HomeViewModel by activityViewModels()

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

        setupBottomSheetBehaviors()
        setupOnBackPressedCallbacks()
        setupTabs()
    }

    private fun setupBottomSheetBehaviors() {
        sheetBehavior = BottomSheetBehavior.from(binding.recipeDetailsContent.recipeBottomSheet)
            .apply {
                addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        onBackPressedCallback.isEnabled = newState == STATE_EXPANDED

                        binding.recipeDetailsContent.recipeBottomSheet.alpha =
                            if (newState == STATE_COLLAPSED) 0f else 1f
                        binding.recipeTabs.alpha = if (newState == STATE_COLLAPSED) 0f else 0.95f
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        //binding.recipeBottomSheet.alpha = slideOffset
                        binding.recipeDetailsContent.recipeBottomSheet.progress = slideOffset
                        //binding.recipesCarousel.alpha = 1f - slideOffset
                        //binding.recipeTitle.alpha = 1f * slideOffset
                        //binding.carouselContainer.alpha = 1f - slideOffset
                        recipeViewModel.setSlideOffset(slideOffset)
                        //binding.titleApp.translationY = (binding.titleApp.height / 2) * -slideOffset
                    }

                })
            }
        setPeekHeight()

        tabsSheetBehavior = BottomSheetBehavior.from(binding.recipeTabs).apply {
            addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    tabsBackPressedCallback.isEnabled = newState == STATE_EXPANDED
                    tabsSheetBehavior.isDraggable = newState == STATE_EXPANDED
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
    }

    private fun setPeekHeight() {
        val carouselImageHeight = requireContext().dimenToPx(R.dimen.carousel_image_height)
        val carouselTopSpacing = requireContext().dimenToPx(R.dimen.carousel_top_spacing)
        this.sheetBehavior.peekHeight =
            (Resources.getSystem().displayMetrics.heightPixels - carouselTopSpacing - carouselImageHeight).toInt()
    }

    private fun setupOnBackPressedCallbacks() {
        onBackPressedCallback =
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, false) {
                sheetBehavior.state = STATE_COLLAPSED
            }

        tabsBackPressedCallback =
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, false) {
                tabsSheetBehavior.state = STATE_COLLAPSED
            }
    }

    private fun setupTabs() {
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.setText(RecipeTabsAdapter.titles[position])
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabsSheetBehavior.state = STATE_EXPANDED
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {
                tabsSheetBehavior.state = STATE_EXPANDED
            }
        })
    }
}