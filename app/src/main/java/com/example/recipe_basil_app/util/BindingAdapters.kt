package com.example.recipe_basil_app.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageUrl")
fun ImageView.imageUrl(url: String?) {
    url?.let { ImageUtil.setImageFromUrl(this, it) }
}