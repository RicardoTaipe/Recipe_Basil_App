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

    private var _binding: FragmentNewHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels { HomeViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentNewHomeBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        modifyConstraintSets(binding.motion)
        animateTitle()
        moveFromMenuToCarousel()
    }

    private fun animateTitle() {
        viewModel.slideOffset.observe(viewLifecycleOwner) {
            binding.titleApp.translationY = (binding.titleApp.height / 2) * -it
        }
    }

    private fun moveFromMenuToCarousel() {
        viewModel.selectedCategory.observe(viewLifecycleOwner) { category ->
            category?.let {
                binding.motion.setTransitionListener(object : MotionLayout.TransitionListener {
                    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                        if (currentId == R.id.start) {
                            viewModel.notifyAnimationFinished()
                        }
                    }

                    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
                    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
                    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
                })
                binding.motion.transitionToStart()
            }
        }
    }

    /**
     * Updates the MotionLayout's constraint sets for start and end states.
     * This method ensures that menu_drawer and fragment_carousel are properly connected and sized.
     */
    private fun modifyConstraintSets(motionLayout: MotionLayout) {
        val screenHeight = resources.displayMetrics.heightPixels
        // Configure the 'start' state
        motionLayout.getConstraintSet(R.id.start)?.let { startSet ->
            // menu_drawer: bottom to parent top (hidden above)
            startSet.connect(R.id.menu_drawer, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            startSet.constrainHeight(R.id.menu_drawer, screenHeight)

            // fragment_carousel: top to parent top, bottom to parent bottom (fully visible)
            startSet.connect(R.id.fragment_carousel, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            startSet.connect(R.id.fragment_carousel, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            startSet.constrainHeight(R.id.fragment_carousel, screenHeight)

            motionLayout.updateState(R.id.start, startSet)
        }

        // Configure the 'end' state
        motionLayout.getConstraintSet(R.id.end)?.let { endSet ->
            // menu_drawer: top to parent top, bottom to parent bottom (fully visible)
            endSet.connect(R.id.menu_drawer, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            endSet.connect(R.id.menu_drawer, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            endSet.constrainHeight(R.id.menu_drawer, screenHeight)

            // fragment_carousel: top to menu_drawer bottom (placed below)
            endSet.connect(R.id.fragment_carousel, ConstraintSet.TOP, R.id.menu_drawer, ConstraintSet.BOTTOM)
            endSet.constrainHeight(R.id.fragment_carousel, screenHeight)

            motionLayout.updateState(R.id.end, endSet)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}