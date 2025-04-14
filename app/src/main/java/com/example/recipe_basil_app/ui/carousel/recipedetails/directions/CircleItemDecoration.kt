package com.example.recipe_basil_app.ui.carousel.recipedetails.directions

import android.graphics.Canvas
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

class CircleItemDecoration(private val colorRes: Int) : RecyclerView.ItemDecoration() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = colorRes
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }
    private var selectedPosition: Int = -1

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        if (selectedPosition != -1) {
            val view = parent.findViewHolderForAdapterPosition(selectedPosition)?.itemView
            view?.let {
                val centerX = view.width / 2f
                val centerY = view.top.toFloat() + view.height / 2f
                val radius = (view.width -4f) / 2f
                c.drawCircle(centerX, centerY, radius, paint)
            }
        }
    }
}
