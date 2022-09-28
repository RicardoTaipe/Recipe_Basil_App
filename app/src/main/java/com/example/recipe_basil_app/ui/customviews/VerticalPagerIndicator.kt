package com.example.recipe_basil_app.ui.customviews

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.recipe_basil_app.R

class VerticalPagerIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //CONST VALUES
    private val paintSelected: Paint
    private val paintSecondary: Paint
    private val paintText: Paint
    private val paddingHorizontal: Float
    private val duration: Int
    private val spacing: Float
    private val gravity: Int

    //DIFFERS
    private var numTextSize: Float
    private var baseDotSize = 0f
    private var cx = 0f
    private var center = 0f
    private var baseOffset = 0f

    private var animator: ValueAnimator? = null
    private var animationOffset: Float = 0f

    private var currentPosition: Int = 0
    private var targetPosition: Int = 0
    private var indicators: ArrayList<Indicator> = arrayListOf()

    //PUBLIC
    var numPages: Int
        get() = indicators.size
        set(value) {
            indicators.clear()
            indicators.addAll(List(value) { Indicator() })
            indicators.trimToSize()
        }

    var indicatorClickListener: ((position: Int) -> Unit)? = null
    var indicatorAnimationListener: ((position: Int) -> Unit)? = null

    init {
        val colorSelected: Int
        val colorSecondary: Int
        val colorText: Int
        val textFont: Typeface
        if (attrs != null) {

            colorSelected = ContextCompat.getColor(context, R.color.bottle_green)
            colorSecondary = ContextCompat.getColor(context, R.color.cosmic_latte)
            colorText = ContextCompat.getColor(context, R.color.bottle_green)
            numTextSize = spToPixel(14f, context)
            textFont = try {
                ResourcesCompat.getFont(
                    context,
                    R.font.montserrat_semibold
                ) ?: Typeface.DEFAULT
            } catch (ex: Exception) {
                Typeface.DEFAULT
            }

            paddingHorizontal = dpToPixel(16f, context)
            duration = 300
            spacing = dpToPixel(24f, context)

            gravity = 0 //fill

        } else {
            colorSelected = ContextCompat.getColor(context, R.color.bottle_green)
            colorText = ContextCompat.getColor(context, R.color.bottle_green)
            numTextSize = spToPixel(14f, context)
            textFont = Typeface.DEFAULT
            paddingHorizontal = dpToPixel(16f, context)
            duration = 300
            spacing = dpToPixel(8f, context)
            gravity = 0
            colorSecondary = ContextCompat.getColor(context, R.color.cosmic_latte)
        }
        paintSelected = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = colorSelected
            style = Paint.Style.STROKE
            strokeWidth = dpToPixel(2f, context)
        }
        paintSecondary = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = colorSecondary
            style = Paint.Style.STROKE
            strokeWidth = dpToPixel(2f, context)
        }
        paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = colorText
            textSize = numTextSize
            typeface = textFont
            isSubpixelText = true
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        animationOffset = currentPosition.toFloat()
        baseDotSize = (width - (paddingHorizontal * 2))
        cx = width / 2f
        center = height / 2f
        baseOffset = baseDotSize / 2 + spacing
        calculateIndicators()
    }

    private fun calculateIndicators() {
        val offset = animationOffset * baseOffset
        for (i in 0 until indicators.size) {
            val dotPos = center + (i * baseOffset) - offset
            indicators[i].y = dotPos
        }
    }

    override fun onDraw(canvas: Canvas?) {
        for (i in 0 until indicators.size) {
            val primary =
                indicators[i].y >= center - baseDotSize / 2 && indicators[i].y <= center + baseDotSize / 2
            canvas?.drawCircle(
                cx,
                indicators[i].y,
                (baseDotSize / 2f) * indicators[i].scale,
                if (primary) paintSelected else paintSecondary
            )

            val text = String.format("%02d", i + 1)
            paintText.textSize = if (primary) {
                spToPixel(28f, context)
            } else {
                spToPixel(14f, context)
            }
            canvas?.drawText(
                text,
                cx - paintText.measureText(text) / 2f,
                indicators[i].y - (paintText.descent() + paintText.ascent()) / 2,
                paintText
            )

        }
    }

    fun moveTo(page: Int = 0) {
        val _page = clampInt(page, 0, indicators.size - 1)
        if (currentPosition == _page && animationOffset == currentPosition.toFloat()) return
        targetPosition = _page
        setupAnimations()
    }

    private fun setupAnimations() {
        animator?.cancel()
        animator?.removeAllUpdateListeners()
        animator = null
        animator = ValueAnimator().apply {
            setFloatValues(animationOffset, targetPosition.toFloat())
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener(updateListener)
        }
        animator?.start()
    }

    private val updateListener = ValueAnimator.AnimatorUpdateListener {
        animationOffset = it.animatedValue as Float
        if (animationOffset == targetPosition.toFloat()) {
            currentPosition = targetPosition
            indicatorAnimationListener?.invoke(currentPosition)
        }
        calculateIndicators()
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            for (i in 0 until indicators.size) {
                if (isInRectangle(cx, indicators[i].y, baseDotSize / 2, event.x, event.y)) {
                    indicatorClickListener?.invoke(i)
                    break
                }
            }
            return performClick()
        }
        return true
    }


    override fun performClick(): Boolean {
        // Give default click listeners priority and perform accessibility/autofill events.
        // Also calls onClickListener() to handle further subclass customizations.
        if (super.performClick()) return true

        return true
    }


    fun initWith(pager: ViewPager2) {
        numPages = pager.adapter?.itemCount ?: 0
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                moveTo(position)
            }
        })
    }

    override fun onSaveInstanceState(): Parcelable {
        val savedState = SavedState(super.onSaveInstanceState()!!)
        savedState._currentPosition = currentPosition
        savedState._numberOfIndicators = numPages
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            currentPosition = state._currentPosition
            numPages = state._numberOfIndicators
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private inner class SavedState : BaseSavedState {
        var _currentPosition = 0
        var _numberOfIndicators = 0

        constructor(source: Parcel) : super(source) {
            _currentPosition = source.readInt()
            _numberOfIndicators = source.readInt()
        }

        constructor(superState: Parcelable) : super(superState)

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(_numberOfIndicators)
            out.writeInt(_currentPosition)
        }

        @JvmField
        val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {

            override fun createFromParcel(source: Parcel): SavedState {
                return SavedState(source)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}