package com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.directions

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_basil_app.databinding.DirectionItemBinding
import com.example.recipe_basil_app.databinding.IndicatorItemBinding
import com.example.recipe_basil_app.network.response.DirectionModel

const val LAYOUT_DIRECTION = 0
const val LAYOUT_INDICATOR = 1

class DirectionsAdapter(private val viewType: Int) :
    ListAdapter<DirectionModel, RecyclerView.ViewHolder>(DirectionModelDiffCallback) {
    var selectedPos = RecyclerView.NO_POSITION

    var listener: ((Int, View) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LAYOUT_DIRECTION) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DirectionItemBinding.inflate(layoutInflater, parent, false)
            ViewHolderDirection(binding)
        } else {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = IndicatorItemBinding.inflate(layoutInflater, parent, false)
            ViewHolderIndicator(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (viewType == LAYOUT_DIRECTION) {
            (holder as ViewHolderDirection).bind(getItem(position))
        } else {
            (holder as ViewHolderIndicator).bind(getItem(position), listener, selectedPos)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }


    class ViewHolderDirection(private val binding: DirectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(directionModel: DirectionModel) {

            binding.apply {
                direction = directionModel
                executePendingBindings()
            }
        }
    }

    class ViewHolderIndicator(private val binding: IndicatorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(directionModel: DirectionModel, listener: ((Int, View) -> Unit)?, selectedPos: Int) {
            val isSelected = selectedPos == adapterPosition
            binding.apply {
                root.setOnClickListener {
                    listener?.invoke(directionModel.id, it)
                }
                indicatorText.apply {
                    text = String.format("%02d", directionModel.id + 1)
                    setTypeface(
                        this.typeface,
                        if (isSelected) Typeface.BOLD else Typeface.NORMAL
                    )
                    scaleX = if (isSelected) 1.75f else 1f
                    scaleY = if (isSelected) 1.75f else 1f
                }
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