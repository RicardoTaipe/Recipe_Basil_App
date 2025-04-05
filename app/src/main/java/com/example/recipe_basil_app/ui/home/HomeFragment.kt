package com.example.recipe_basil_app.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.recipe_basil_app.R
import com.example.recipe_basil_app.databinding.FragmentNewHomeBinding
import com.example.recipe_basil_app.ui.carousel.container.RecipeCarouselAdapter
import com.example.recipe_basil_app.ui.menudrawer.MenuAdapter
import com.example.recipe_basil_app.util.dimenToPx
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentNewHomeBinding
    private val categoryAdapter by lazy { MenuAdapter() }
    private val pagerAdapter by lazy { RecipeCarouselAdapter() }
    private lateinit var viewPagerChangeCallback: OnPageChangeCallback
    private lateinit var sheetBehavior: BottomSheetBehavior<MotionLayout>

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewHomeBinding.inflate(inflater, container, false)
        modifyConstraintSets(binding.motion)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setUpMenuDrawer()

        //setUpCarousel()

        //setUpRecipeDetailsContainer()

        setUpRecipeTabs()

        //viewModel.retrieveRecipesByCategory(null)

        moveFromMenuToCarousel()
    }

    private fun moveFromMenuToCarousel() {
        viewModel.selectedCategory.observe(viewLifecycleOwner) {
            it?.let {
                binding.motion.transitionToStart()
            }
        }
    }

//    private fun setUpMenuDrawer() {
//        binding.menuDrawer.categoriesRecyclerview.adapter = categoryAdapter
//
//        categoryAdapter.itemClickListener = { category, position ->
//            categoryAdapter.selectedPos = position
//            categoryAdapter.notifyDataSetChanged()
//            //setFragmentResult(REQUEST_CATEGORY, bundleOf(CATEGORY_SELECTED to category.strCategory))
//        }
//        viewModel.categories.observe(viewLifecycleOwner) {
//            categoryAdapter.submitList(it)
//        }
//    }

//    private fun setUpCarousel() {
//        binding.carouselContainer.recipesCarousel.adapter = pagerAdapter
//
//        viewModel.recipeByCategory.observe(viewLifecycleOwner) { recipes ->
//            recipes?.let {
//                pagerAdapter.submitList(it)
//            }
//        }
//
//        viewPagerChangeCallback = object :
//            OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                viewModel.itemSelected(position)
//            }
//        }
//        binding.carouselContainer.recipesCarousel.registerOnPageChangeCallback(viewPagerChangeCallback)
//        applyParallaxAnimation()
//    }

    private fun setUpRecipeDetailsContainer() {
        //setPeekHeight()
        //animateWhenBottomSheetIsDragged()
    }

    private fun setUpRecipeTabs() {

    }

//    private fun animateWhenBottomSheetIsDragged() {
//        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                //backCallback.isEnabled = newState == BottomSheetBehavior.STATE_EXPANDED
//                binding.recipeDetailsContainer.recipeDetailsContainer.recipeBottomSheet.alpha = if(newState == BottomSheetBehavior.STATE_COLLAPSED ) 0f else 1f
//                //TODO fix how to disable this!! this was working
//                //before using Fragmetn receipe carousel container
//                //binding.carouselContainer.recipesCarousel.isUserInputEnabled = newState != BottomSheetBehavior.STATE_EXPANDED
//            }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                Log.d("offset", slideOffset.toString())
//                //binding.recipeBottomSheet.alpha = slideOffset
//                binding.recipeDetailsContainer.recipeDetailsContainer.recipeBottomSheet.progress = slideOffset
//                //binding.recipesCarousel.alpha = 1f - slideOffset
//                //binding.recipeTitle.alpha = 1f * slideOffset
//                //binding.carouselContainer.alpha = 1f - slideOffset
//                binding.titleApp.translationY = (binding.titleApp.height / 2) * -slideOffset
//            }
//
//        })
//    }

//    private fun setPeekHeight() {
//        sheetBehavior = BottomSheetBehavior.from(binding.recipeDetailsContainer.recipeBottomSheet)
//        val carouselImageHeight = requireContext().dimenToPx(R.dimen.carousel_image_height)
//        val carouselTopSpacing = requireContext().dimenToPx(R.dimen.carousel_top_spacing)
//        sheetBehavior.peekHeight =
//            (Resources.getSystem().displayMetrics.heightPixels - carouselTopSpacing - carouselImageHeight).toInt()
//    }

    private fun getBottomSheetReference(): Float {
        val outValue = TypedValue()
        resources.getValue(R.dimen.carousel_image_height, outValue, true)
        return outValue.float
    }

//    private fun applyParallaxAnimation() {
//        lateinit var nameRecipe: TextView
//        lateinit var imageRecipe: ImageView
//        binding.carouselContainer.recipesCarousel.setPageTransformer { page, position ->
//            nameRecipe = page.findViewById(R.id.meal_name)
//            imageRecipe = page.findViewById(R.id.meal_image)
//            when {
//                position < -1 -> // [-Infinity,-1) This page is way off-screen to the left.
//                    page.alpha = 1f
//
//                position <= 1 -> { // [-1,1]
//                    nameRecipe.translationX = position * (page.width)
//                    imageRecipe.translationX = -position * (page.width)
//                }
//
//                else -> // (1,+Infinity] This page is way off-screen to the right.
//                    page.alpha = 1f
//            }
//        }
//    }

//    override fun onDestroy() {
//        super.onDestroy()
//        binding.carouselContainer.recipesCarousel.unregisterOnPageChangeCallback(viewPagerChangeCallback)
//    }

    private fun modifyConstraintSets(motionLayout: MotionLayout) {
        val screenHeight = resources.displayMetrics.heightPixels

        val stateConfigurations = listOf(
            //ConstraintSetStart
            R.id.start to mapOf(
                R.id.menu_drawer to Pair(ConstraintSet.BOTTOM, ConstraintSet.TOP),
                R.id.fragment_carousel to Pair(ConstraintSet.TOP, ConstraintSet.TOP),
                R.id.fragment_carousel to Pair(ConstraintSet.BOTTOM, ConstraintSet.BOTTOM)
            ),
            //ConstraintSetEnd
            R.id.end to mapOf(
                R.id.menu_drawer to Pair(ConstraintSet.TOP, ConstraintSet.TOP),
                R.id.menu_drawer to Pair(ConstraintSet.BOTTOM, ConstraintSet.BOTTOM),
                R.id.fragment_carousel to Pair(ConstraintSet.TOP, ConstraintSet.BOTTOM)
            )
        )

        for ((stateId, viewConfigurations) in stateConfigurations) {
            val constraintSet = motionLayout.getConstraintSet(stateId)?.apply {
                for ((viewId, connection) in viewConfigurations) {
                    connect(viewId, connection.first, ConstraintSet.PARENT_ID, connection.second)
                    constrainHeight(viewId, screenHeight)
                }
            }
            motionLayout.updateState(stateId, constraintSet)
        }
    }
}