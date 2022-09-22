package com.example.recipe_basil_app.ui.menudrawer

import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_basil_app.databinding.CategoryMenuItemBinding
import com.example.recipe_basil_app.network.response.Category

class MenuAdapter : ListAdapter<Category, MenuViewHolder>(CategoryDiff) {
    var itemClickListener: ((Category, position: Int) -> Unit)? = null
    var selectedPos = RecyclerView.NO_POSITION
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = CategoryMenuItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MenuViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(getItem(position), selectedPos)
    }
}

class MenuViewHolder(
    private val binding: CategoryMenuItemBinding,
    private val itemClickListener: ((Category, position: Int) -> Unit)?
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Category?, selectedPos: Int) {
        val isSelected = selectedPos == adapterPosition

        binding.apply {
            root.setOnClickListener {
                itemClickListener?.invoke(category!!, adapterPosition)
            }

            mealName.apply {
                this.isSelected = isSelected
                paintFlags = if (isSelected) {
                    mealName.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                } else {
                    mealName.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
                }
                setTypeface(
                    mealName.typeface,
                    if (isSelected) Typeface.BOLD else Typeface.NORMAL
                )
            }
            this.category = category
            executePendingBindings()
        }
    }
}

object CategoryDiff : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.strCategory == newItem.strCategory
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}
