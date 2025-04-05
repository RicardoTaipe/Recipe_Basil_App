package com.example.recipe_basil_app.ui.carousel.container

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.recipe_basil_app.R
import com.example.recipe_basil_app.databinding.FragmentRecipeContainerBinding
import com.example.recipe_basil_app.ui.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


const val PAGE_SELECTED = "page_selected"
const val REQUEST_PAGE = "request_page"

class RecipeContainerFragment : Fragment() {

    //private val viewModel: RecipeContainerViewModel by viewModels()
    private val viewModel: HomeViewModel by viewModels({ requireParentFragment() })
    private lateinit var binding: FragmentRecipeContainerBinding
    private lateinit var sheetBehavior: BottomSheetBehavior<FragmentContainerView>
    private val pagerAdapter by lazy { RecipeCarouselAdapter() }
    private lateinit var viewPagerChangeCallback: OnPageChangeCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setFragmentResultListener(REQUEST_CATEGORY) { _, bundle ->
//            val category = bundle.getString(CATEGORY_SELECTED)
//            category?.let {
//                viewModel.retrieveRecipesByCategory(it)
//                setFragmentResult(REQUEST_PAGE, bundleOf(PAGE_SELECTED to RECIPES_PAGE))
//            }
//
//        }

    }

    override fun onResume() {
        super.onResume()
        Log.d("LIFECYCLE", "resumed")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIFECYCLE", "paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LIFECYCLE", "stoped")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeContainerBinding.inflate(inflater, container, false)
//
//        binding.recipesCarousel.adapter = pagerAdapter
//
//        lateinit var nameRecipe: TextView
//        lateinit var imageRecipe: ImageView
//
//        binding.recipesCarousel.setPageTransformer { page, position ->
//            nameRecipe = page.findViewById(R.id.meal_name)
//            imageRecipe = page.findViewById(R.id.meal_image)
//            when {
//                position < -1 -> // [-Infinity,-1) This page is way off-screen to the left.
//                    page.alpha = 1f
//                position <= 1 -> { // [-1,1]
//                    nameRecipe.translationX = position * (page.width)
//                    imageRecipe.translationX = -position * (page.width)
//                }
//                else -> // (1,+Infinity] This page is way off-screen to the right.
//                    page.alpha = 1f
//            }
//        }
//        viewPagerChangeCallback = object :
//            OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                viewModel.itemSelected(position)
//            }
//        }
//        binding.recipesCarousel.registerOnPageChangeCallback(viewPagerChangeCallback)
//        binding.recipeBottomSheet.setOnClickListener {
//            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//        }
//
//        sheetBehavior = BottomSheetBehavior.from(binding.recipeBottomSheet)
//        val bottomSheetRef = 1.0f - getFloatDimension(R.dimen.bottom_sheet_ref)
//        sheetBehavior.peekHeight =
//            (bottomSheetRef * Resources.getSystem().displayMetrics.heightPixels).toInt()
//
//        val backCallback =
//            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, false) {
//                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//            }
//
//        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                backCallback.isEnabled = newState == BottomSheetBehavior.STATE_EXPANDED
////                requireActivity().findViewById<ViewPager2>(R.id.landing_pager)
////                    .isUserInputEnabled = newState != BottomSheetBehavior.STATE_EXPANDED
//            }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                binding.recipeBottomSheet.alpha = slideOffset
//                binding.recipesCarousel.alpha = 1f - slideOffset
//                binding.expandMoreButton.alpha = 1f - slideOffset
//                binding.titleApp.translationY = (-binding.titleApp.height * slideOffset)
//            }
//
//        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recipesCarousel.adapter = pagerAdapter
        binding.recipesCarousel.offscreenPageLimit = 3

        viewModel.recipeByCategory.observe(viewLifecycleOwner) { recipes ->
            recipes?.let {
                viewLifecycleOwner.lifecycleScope.launch {
                    // Wait for menu drawer animation to end to allow ViewPager refresh.
                    delay(200)
                    pagerAdapter.submitList(it)
                }
            }
        }

        viewPagerChangeCallback = object :
            OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.selectRecipe(position)
            }
        }
        binding.recipesCarousel.registerOnPageChangeCallback(viewPagerChangeCallback)
        applyParallaxAnimation()

        viewModel.selectedCategory.observe(viewLifecycleOwner) {
            viewModel.retrieveRecipesByCategory(it.strCategory)
        }
        viewModel.retrieveRecipesByCategory(null)
    }

    private fun applyParallaxAnimation() {
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