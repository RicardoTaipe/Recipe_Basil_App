package com.example.recipe_basil_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.recipe_basil_app.R
import com.example.recipe_basil_app.databinding.FragmentNewHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentNewHomeBinding
    private val viewModel: HomeViewModel by activityViewModels { HomeViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewHomeBinding.inflate(inflater, container, false)
        modifyConstraintSets(binding.motion)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animateTitle()
        moveFromMenuToCarousel()
    }

    private fun animateTitle() {
        viewModel.slideOffset.observe(viewLifecycleOwner) {
            binding.titleApp.translationY = (binding.titleApp.height / 2) * -it
        }
    }

    private fun moveFromMenuToCarousel() {
        viewModel.selectedCategory.observe(viewLifecycleOwner) {
            it?.let {
                binding.motion.transitionToStart()
            }
        }
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