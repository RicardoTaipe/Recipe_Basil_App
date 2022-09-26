package com.example.recipe_basil_app.ui.carousel.container

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.annotation.DimenRes
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.recipe_basil_app.R
import com.example.recipe_basil_app.databinding.FragmentRecipeContainerBinding
import com.example.recipe_basil_app.ui.home.RECIPES_PAGE
import com.example.recipe_basil_app.ui.menudrawer.CATEGORY_SELECTED
import com.example.recipe_basil_app.ui.menudrawer.REQUEST_CATEGORY
import com.google.android.material.bottomsheet.BottomSheetBehavior


const val PAGE_SELECTED = "page_selected"
const val REQUEST_PAGE = "request_page"

class RecipeContainerFragment : Fragment() {

    private val viewModel: RecipeContainerViewModel by viewModels()
    private lateinit var binding: FragmentRecipeContainerBinding
    private lateinit var sheetBehavior: BottomSheetBehavior<FragmentContainerView>
    private lateinit var pagerAdapter: RecipeCarouselAdapter
    private lateinit var viewPagerChangeCallback: OnPageChangeCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_CATEGORY) { _, bundle ->
            val category = bundle.getString(CATEGORY_SELECTED)
            category?.let {
                viewModel.retrieveRecipesByCategory(it)
                setFragmentResult(REQUEST_PAGE, bundleOf(PAGE_SELECTED to RECIPES_PAGE))
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeContainerBinding.inflate(inflater, container, false)

        pagerAdapter = RecipeCarouselAdapter()

        binding.recipesCarousel.adapter = pagerAdapter

        lateinit var nameRecipe: TextView
        lateinit var imageRecipe: ImageView

        binding.recipesCarousel.setPageTransformer { page, position ->
            nameRecipe = page.findViewById(R.id.meal_name)
            imageRecipe = page.findViewById(R.id.meal_image)
            when {
                position < -1 -> // [-Infinity,-1) This page is way off-screen to the left.
                    page.alpha = 1f
                position <= 1 -> { // [-1,1]
                    nameRecipe.translationX = position * (page.width)
                    imageRecipe.translationX = -position * (page.width)
                }
                else -> // (1,+Infinity] This page is way off-screen to the right.
                    page.alpha = 1f
            }
        }
        viewPagerChangeCallback = object :
            OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.itemSelected(position)
            }
        }
        binding.recipesCarousel.registerOnPageChangeCallback(viewPagerChangeCallback)
        binding.recipeBottomSheet.setOnClickListener {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        sheetBehavior = BottomSheetBehavior.from(binding.recipeBottomSheet)
        val bottomSheetRef = 1.0f - getFloatDimension(R.dimen.bottom_sheet_ref)
        sheetBehavior.peekHeight =
            (bottomSheetRef * Resources.getSystem().displayMetrics.heightPixels).toInt()

        val backCallback =
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, false) {
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                backCallback.isEnabled = newState == BottomSheetBehavior.STATE_EXPANDED
                requireActivity().findViewById<ViewPager2>(R.id.landing_pager)
                    .isUserInputEnabled = newState != BottomSheetBehavior.STATE_EXPANDED
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.recipeBottomSheet.alpha = slideOffset
                binding.recipesCarousel.alpha = 1f - slideOffset
                binding.expandMoreButton.alpha = 1f - slideOffset
                binding.titleApp.translationY = (-binding.titleApp.height * slideOffset)
            }

        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.meals.observe(viewLifecycleOwner) { recipes ->
            recipes?.let {
                pagerAdapter.submitList(it.take(5))
            }
        }
        viewModel.retrieveRecipesByCategory(null)
    }

    private fun getFloatDimension(@DimenRes id: Int): Float {
        val outValue = TypedValue()
        resources.getValue(id, outValue, true)
        return outValue.float
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.recipesCarousel.unregisterOnPageChangeCallback(viewPagerChangeCallback)
    }
}