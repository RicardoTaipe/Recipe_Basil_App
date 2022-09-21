package com.example.recipe_basil_app.util

import android.widget.ImageView

fun ImageView.imageUrl(url: String?) {
    url?.let { ImageUtil.setImageFromUrl(this, it) }
}