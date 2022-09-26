package com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.directions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_basil_app.databinding.DirectionItemBinding
import com.example.recipe_basil_app.network.response.DirectionModel

class DirectionsAdapter :
    ListAdapter<DirectionModel, DirectionsAdapter.ViewHolder>(DirectionModelDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DirectionItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: DirectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(directionModel: DirectionModel) {

            binding.apply {
                direction = directionModel
                //descriptionText = directionModel.description
                executePendingBindings()
            }
        }
    }

}

object DirectionModelDiffCallback : DiffUtil.ItemCallback<DirectionModel>() {
    override fun areItemsTheSame(oldItem: DirectionModel, newItem: DirectionModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DirectionModel, newItem: DirectionModel): Boolean {
        return oldItem == newItem
    }

}