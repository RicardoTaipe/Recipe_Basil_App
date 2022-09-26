package com.example.recipe_basil_app.ui.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import com.google.android.material.color.MaterialColors


class TextViewDotted @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    private var borderWidth = 4.0f

    // Paint object for coloring and styling
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = borderWidth
        pathEffect = DashPathEffect(floatArrayOf(4f, 16f), 0f)
    }
    private val mPath = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color =
            MaterialColors.getColor(this, com.google.android.material.R.attr.colorPrimaryDark)

        val posX = getPaint().measureText(text as String?) + 10f
        val posY = (measuredHeight / 2f) - (getPaint().fontMetrics.ascent / 2f) - borderWidth
        mPath.moveTo(posX, posY)
        mPath.quadTo(
            (width / 2).toFloat(), posY, width.toFloat(),
            posY
        )

        canvas.drawPath(mPath, paint)
    }
}