package com.example.recipe_basil_app.util

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageUtil {
    fun setImageFromUrl(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
        //.placeholder(R.drawable.image_placeholder)
        //.error(R.drawable.image_placeholder)

    }
}