package com.example.recipe_basil_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.recipe_basil_app.databinding.FragmentHomeBinding
import com.example.recipe_basil_app.ui.carousel.container.PAGE_SELECTED
import com.example.recipe_basil_app.ui.carousel.container.REQUEST_PAGE


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_PAGE) { _, bundle ->
            val page = bundle.getInt(PAGE_SELECTED)
            binding.apply {
                landingPager.setCurrentItem(RECIPES_PAGE, true)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.apply {
            landingPager.adapter = LandingPagerAdapter(requireActivity())
            landingPager.offscreenPageLimit = 3
            landingPager.setCurrentItem(RECIPES_PAGE, false)
        }
        return binding.root
    }
}