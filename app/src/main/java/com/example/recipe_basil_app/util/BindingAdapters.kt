package com.example.recipe_basil_app.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageUrl")
fun bindImage(view: ImageView, imgUrl: String?) {
    imgUrl?.let { ImageUtil.setImageFromUrl(view, it) }
}