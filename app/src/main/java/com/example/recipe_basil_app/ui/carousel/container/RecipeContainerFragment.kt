package com.example.recipe_basil_app.ui.carousel.container

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.*
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
    private lateinit var pagerAdapter: RecipePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_CATEGORY) { _, bundle ->
            val category = bundle.getString(CATEGORY_SELECTED)
            Log.d("RecipeContainerFragment", category.toString())
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

        pagerAdapter = RecipePagerAdapter(requireActivity(), emptyList())

        binding.recipesCarousel.adapter = pagerAdapter
        binding.recipesCarousel.offscreenPageLimit = 5
        binding.recipesCarousel.setPageTransformer { page, position ->
            val name: TextView = page.findViewById(R.id.meal_name)
            val card: ImageView = page.findViewById(R.id.meal_image)
            if (position <= 1 && position >= -1) {
                name.translationX = position * (page.width / 4f)
                card.translationX = -position * (page.width / 8f)
                /* If user drags the page right to left :
                Planet : 0.5 of normal speed
                Name : 1.25 of normal speed
                If the user drags the page left to right :
                Planet: 1.5 of normal speed
                Name: 0.75 of normal speed
                */
            }
        }
        binding.recipeBottomSheet.setOnClickListener {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        sheetBehavior = BottomSheetBehavior.from(binding.recipeBottomSheet)
        sheetBehavior.peekHeight =
            (0.35 * Resources.getSystem().displayMetrics.heightPixels).toInt()
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("RecipeFragment", slideOffset.toString())
                binding.titleApp.translationY = (-binding.titleApp.height * slideOffset)
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.meals.observe(viewLifecycleOwner) { recipes ->
            recipes?.let {
                pagerAdapter.recipes = it.take(5)
                it.take(5).forEachIndexed { index, meal ->
                    pagerAdapter.notifyItemChanged(index, meal)
                }
                binding.recipesCarousel.adapter = pagerAdapter
            }
        }
        viewModel.retrieveRecipesByCategory(null)
    }

    companion object {
        fun newInstance() = RecipeContainerFragment()
    }
}