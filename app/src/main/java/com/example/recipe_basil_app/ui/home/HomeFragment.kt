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
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.recipe_basil_app.R
import com.example.recipe_basil_app.databinding.FragmentNewHomeBinding
import com.example.recipe_basil_app.ui.carousel.container.RecipeCarouselAdapter
import com.example.recipe_basil_app.ui.menudrawer.MenuAdapter
import com.example.recipe_basil_app.util.dimenToPx
import com.google.android.material.bottomsheet.BottomSheetBehavior


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

        setUpCarousel()

        setUpRecipeDetailsContainer()

        setUpRecipeTabs()

        viewModel.retrieveRecipesByCategory(null)
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

    private fun setUpCarousel() {
        binding.carouselContainer.recipesCarousel.adapter = pagerAdapter

        viewModel.recipeByCategory.observe(viewLifecycleOwner) { recipes ->
            recipes?.let {
                pagerAdapter.submitList(it)
            }
        }

        viewPagerChangeCallback = object :
            OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.itemSelected(position)
            }
        }

        applyParallaxAnimation()
    }

    private fun setUpRecipeDetailsContainer() {
        setPeekHeight()
        animateWhenBottomSheetIsDragged()
    }

    private fun setUpRecipeTabs() {

    }

    private fun animateWhenBottomSheetIsDragged() {
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //backCallback.isEnabled = newState == BottomSheetBehavior.STATE_EXPANDED
                binding.carouselContainer.recipesCarousel.isUserInputEnabled = newState != BottomSheetBehavior.STATE_EXPANDED
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("offset", slideOffset.toString())
                //binding.recipeBottomSheet.alpha = slideOffset
                binding.recipeDetailsContainer.recipeBottomSheet.progress = slideOffset
                //binding.recipesCarousel.alpha = 1f - slideOffset
                //binding.recipeTitle.alpha = 1f * slideOffset
                //binding.carouselContainer.alpha = 1f - slideOffset
                binding.titleApp.translationY = (binding.titleApp.height/2) * -slideOffset
            }

        })
    }

    private fun setPeekHeight() {
        sheetBehavior = BottomSheetBehavior.from(binding.recipeDetailsContainer.recipeBottomSheet)
        val carouselImageHeight = requireContext().dimenToPx(R.dimen.carousel_image_height)
        val carouselTopSpacing = requireContext().dimenToPx(R.dimen.carousel_top_spacing)
        sheetBehavior.peekHeight =
            (Resources.getSystem().displayMetrics.heightPixels - carouselTopSpacing - carouselImageHeight).toInt()
    }

    private fun getBottomSheetReference(): Float {
        val outValue = TypedValue()
        resources.getValue(R.dimen.carousel_image_height, outValue, true)
        return outValue.float
    }

    private fun applyParallaxAnimation() {
        lateinit var nameRecipe: TextView
        lateinit var imageRecipe: ImageView
        binding.carouselContainer.recipesCarousel.setPageTransformer { page, position ->
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

    override fun onDestroy() {
        super.onDestroy()
        binding.carouselContainer.recipesCarousel.unregisterOnPageChangeCallback(viewPagerChangeCallback)
    }

    private fun modifyConstraintSets(motionLayout: MotionLayout) {

        val screenHeight = resources.displayMetrics.heightPixels

        val startConstraintSet = motionLayout.getConstraintSet(R.id.start)
        val endConstraintSet = motionLayout.getConstraintSet(R.id.end)


        startConstraintSet?.apply {
            setVisibility(R.id.menu_drawer, ConstraintSet.VISIBLE)
            connect(
                R.id.menu_drawer,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            connect(
                R.id.menu_drawer,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            connect(
                R.id.menu_drawer,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
            constrainHeight(R.id.menu_drawer, screenHeight)

            setVisibility(R.id.carousel_container, ConstraintSet.VISIBLE)
            connect(
                R.id.fragment_carousel,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            connect(
                R.id.fragment_carousel,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            connect(
                R.id.fragment_carousel,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
            connect(
                R.id.fragment_carousel,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            constrainHeight(R.id.fragment_carousel, screenHeight)
        }

        endConstraintSet?.apply {
            setVisibility(R.id.menu_drawer, ConstraintSet.VISIBLE)
            connect(
                R.id.menu_drawer,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            connect(
                R.id.menu_drawer,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            connect(
                R.id.menu_drawer,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
            connect(
                R.id.menu_drawer,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            constrainHeight(R.id.menu_drawer, screenHeight)

            setVisibility(R.id.fragment_carousel, ConstraintSet.VISIBLE)
            connect(
                R.id.fragment_carousel,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            connect(
                R.id.fragment_carousel,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            connect(
                R.id.fragment_carousel,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
            constrainHeight(R.id.fragment_carousel, screenHeight)
        }


        motionLayout.updateState(R.id.start, startConstraintSet)
        motionLayout.updateState(R.id.end, endConstraintSet)
    }
}