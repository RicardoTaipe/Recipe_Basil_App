package com.example.recipe_basil_app.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageUrl")
fun ImageView.imageUrl(url: String?) {
    url?.let { ImageUtil.setImageFromUrl(this, it) }
}

// Extension function to convert dp from dimens.xml to pixels
fun Context.dimenToPx(dimenResId: Int): Float {
    return resources.getDimension(dimenResId)
}