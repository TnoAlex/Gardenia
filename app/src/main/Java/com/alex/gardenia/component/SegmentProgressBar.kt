package com.alex.gardenia.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.alex.gardenia.R


class SegmentProgressBar : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initDefaultValues(context, attrs!!, defStyleAttr)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initDefaultValues(context, attrs, defStyleAttr)
    }


    private val DEFAULT_HEIGHT_PROGRESS_BAR = 10

    private val mRadius = 60f

    private var defaultBackgroundColor: Int = Color.parseColor("#DDE4F4")

    private var defaultProgressBarColor: Int = Color.parseColor("#3D7EFE")

    private var mPaint: Paint = Paint()
    private var mOffset = 0f
    private var mDefaultOffset = 10f
    private var mProgressBarHeight: Int = dp2px(DEFAULT_HEIGHT_PROGRESS_BAR)

    private var mRealWidth = 0f

    private var mMax = 100

    private var mProgress = 0

    private var progressWith = 0f

    @SuppressLint("CustomViewStyleable")
    private fun initDefaultValues(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressBar,
            defStyleAttr,
            defStyleAttr
        )
        mProgress = arr.getInt(R.styleable.ProgressBar_progress, 0)
        mMax = arr.getInt(R.styleable.ProgressBar_max, 0)
        defaultBackgroundColor = arr.getColor(
            R.styleable.ProgressBar_progressBackground,
            Color.parseColor("#DDE4F4")
        )
        defaultProgressBarColor =
            arr.getColor(R.styleable.ProgressBar_progressBarColor, Color.parseColor("#3D7EFE"))
        arr.recycle()
    }

    fun setMax(max: Int) {
        mMax = max
        if (max > 0) {
            mOffset = mRealWidth / mMax / 8
            if (mOffset > mDefaultOffset) {
                mOffset = mDefaultOffset
            }
            progressWith = (mRealWidth - (mMax - 1) * mOffset) / mMax
        }
        invalidate()
    }

    fun setProgress(progress: Int) {
        mProgress = progress
        invalidate()
    }

    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        //??????
        val height: Int = measureHeight(heightMeasureSpec)
        //??????????????????????????????View????????????????????????????????????
        setMeasuredDimension(width, height)
        //?????????????????????????????????padding
        mRealWidth = (measuredWidth - paddingRight - paddingLeft).toFloat()
        //????????????????????????????????????
        if (mMax > 0) {
            mOffset = mRealWidth / mMax / 8
            if (mOffset > mDefaultOffset) {
                mOffset = mDefaultOffset
            }
            progressWith = (mRealWidth - (mMax - 1) * mOffset) / mMax
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //?????????????????????????????????padding
        mRealWidth = (w - paddingRight - paddingLeft).toFloat()
        //????????????????????????????????????
        if (mMax > 0) {
            mOffset = mRealWidth / mMax / 8
            if (mOffset > mDefaultOffset) {
                mOffset = mDefaultOffset
            }
            progressWith = (mRealWidth - (mMax - 1) * mOffset) / mMax
        }
        invalidate()
    }

    private fun measureHeight(measureSpec: Int): Int {
        var result = 0
        //????????????????????????????????????
        val specMode = MeasureSpec.getMode(measureSpec)
        //?????????????????????????????????
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = (paddingTop + paddingBottom + mProgressBarHeight)
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        return result
    }

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.color = defaultBackgroundColor
        //??????????????????
        mPaint.style = Paint.Style.FILL
        //????????????
        mPaint.isAntiAlias = true
        //????????????????????????????????????
        canvas.drawRoundRect(0F, 0F, mRealWidth, height.toFloat(), mRadius, mRadius, mPaint)
        //???????????????
        mPaint.color = defaultProgressBarColor
        for (i in 0 until mProgress) {
            canvas.drawRoundRect(
                i * (progressWith + mOffset), 0F, progressWith + i * (progressWith + mOffset),
                height.toFloat(), mRadius, mRadius, mPaint
            )
        }
    }

    private fun dp2px(dpVal: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal.toFloat(), resources.displayMetrics
        ).toInt()
    }
}