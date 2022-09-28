package com.example.recipe_basil_app.ui.customviews

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun dpToPixel(dp: Float, context: Context): Float {
    return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param px A value in px (pixels) unit. Which we need to convert into db
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun pixelsToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun spToPixel(sp: Float, context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        context.resources.displayMetrics
    )
}

fun clampInt(target: Int, minVal: Int, maxVal: Int): Int {
    return min(max(target, minVal), maxVal)
}

fun isInRectangle(
    centerX: Float, centerY: Float, radius: Float,
    x: Float, y: Float
): Boolean {
    return x >= centerX - radius && x <= centerX + radius &&
            y >= centerY - radius && y <= centerY + radius
}

fun isPointInCircle(
    centerX: Float, centerY: Float,
    radius: Float, x: Float, y: Float
): Boolean {
    if (isInRectangle(centerX, centerY, radius, x, y)) {
        var dx = centerX - x
        var dy = centerY - y
        dx *= dx
        dy *= dy
        val distanceSquared = dx + dy
        val radiusSquared = radius * radius
        return distanceSquared <= radiusSquared
    }
    return false
}