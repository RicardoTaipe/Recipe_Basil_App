package com.example.recipe_basil_app.ui.menudrawer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_basil_app.databinding.CategoryMenuItemBinding
import com.example.recipe_basil_app.network.response.Category

class MenuAdapter : ListAdapter<Category, MenuViewHolder>(CategoryDiff) {
    var itemClickListener: ((Category) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = CategoryMenuItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MenuViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MenuViewHolder(
    private val binding: CategoryMenuItemBinding,
    private val itemClickListener: ((Category) -> Unit)?
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(category: Category?) {
        binding.root.setOnClickListener {
            itemClickListener?.invoke(category!!)
        }
        binding.category = category
        binding.executePendingBindings()
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
