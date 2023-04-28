package com.example.recipe_basil_app.ui.carousel.recipe.recipedetailstabs.directions

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

class CircleItemDecoration(private val color: Int) : RecyclerView.ItemDecoration() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
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
                paint.style = Paint.Style.FILL
                paint.color = Color.TRANSPARENT
                c.drawCircle(centerX, centerY, radius, paint)


                paint.color = this.color
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 4f
                c.drawCircle(centerX, centerY, radius, paint)
            }
        }
    }
}
