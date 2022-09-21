package com.example.recipe_basil_app.ui.carousel.container

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_basil_app.databinding.CarouselItemBinding
import com.example.recipe_basil_app.network.response.Meal
import com.example.recipe_basil_app.util.imageUrl

class RecipeCarouselAdapter :
    ListAdapter<Meal, RecipeCarouselAdapter.ViewHolder>(MealDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CarouselItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: CarouselItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.apply {
                //carousel = meal
                mealName.text = meal.strMeal
                mealImage.imageUrl(meal.strMealThumb)
                executePendingBindings()
            }
        }
    }
}

object MealDiffCallback : DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem == newItem
    }
}