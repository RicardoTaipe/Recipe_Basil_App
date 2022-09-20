package com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.example.recipe_basil_app.databinding.FragmentRecipeDetailsTabBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class RecipeDetailsTabFragment : Fragment(), TabLayout.OnTabSelectedListener {
    private lateinit var binding: FragmentRecipeDetailsTabBinding
    private lateinit var adapter: RecipeTabsAdapter
    private lateinit var sheetBehavior: BottomSheetBehavior<NestedScrollView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailsTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecipeTabsAdapter(this)
        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = RecipeTabsAdapter.titles[position]
        }.attach()

        sheetBehavior = BottomSheetBehavior.from(binding.bottomSheetTabs)

        val backCallback =
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, false) {
                sheetBehavior.state = STATE_COLLAPSED
            }

        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                backCallback.isEnabled = newState == STATE_EXPANDED
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        binding.tabLayout.addOnTabSelectedListener(this)

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        sheetBehavior.state = STATE_EXPANDED
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {
        sheetBehavior.state = STATE_EXPANDED
    }
}