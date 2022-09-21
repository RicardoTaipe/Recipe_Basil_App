package com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.ingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_basil_app.databinding.IngredientItemBinding
import com.example.recipe_basil_app.network.response.IngredientModel
import com.example.recipe_basil_app.util.imageUrl

class IngredientsAdapter :
    ListAdapter<IngredientModel, IngredientsAdapter.ViewHolder>(IngredientModelDiffCallback) {

    interface IngredientsAdapterListener {
        fun onMovieClicked(movie: IngredientModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = IngredientItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: IngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredientModel: IngredientModel) {
            binding.apply {
                ingredient = ingredientModel
                ingredientText.text = ingredientModel.name.padEnd(20, '.')
                ingredientImage.imageUrl(ingredientModel.imageUrl)
                executePendingBindings()
            }
        }
    }
}

object IngredientModelDiffCallback : DiffUtil.ItemCallback<IngredientModel>() {
    override fun areItemsTheSame(oldItem: IngredientModel, newItem: IngredientModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: IngredientModel, newItem: IngredientModel): Boolean {
        return oldItem == newItem
    }

}