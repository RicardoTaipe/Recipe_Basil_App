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
import androidx.core.view.isVisible
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
    private lateinit var recipeTabsAdapter: RecipeTabsAdapter
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
        setupAdapter()
        setupBottomSheetBehaviors()
        setupOnBackPressedCallbacks()
        setupTabs()
    }

    private fun setupAdapter() {
        recipeTabsAdapter = RecipeTabsAdapter(this)
        binding.pager.adapter = recipeTabsAdapter
    }

    private fun setupBottomSheetBehaviors() {
        sheetBehavior = BottomSheetBehavior.from(binding.recipeDetailsContent.recipeBottomSheet)
            .apply {
                addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        onBackPressedCallback.isEnabled = newState == STATE_EXPANDED
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        binding.recipeDetailsContent.recipeBottomSheet.progress = slideOffset
                        recipeViewModel.setSlideOffset(slideOffset)
                        binding.recipeTabs.isVisible = (slideOffset > 0.5f)
                        binding.recipeDetailsContent.recipeBottomSheet.alpha = if (slideOffset > 0f) 1f else 0f
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

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    val scale = 1 - (0.2f * slideOffset)  // Scale from 1 to 0.8
                    binding.recipeDetailsContent.recipeBottomSheet.apply {
                        scaleX = scale
                        scaleY = scale
                    }
                }
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