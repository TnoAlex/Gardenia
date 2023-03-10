package com.alex.gardenia.component

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CalendarListDecoration() : RecyclerView.ItemDecoration() {

    private val topSpacing = 0
    private val leftSpacing = 10
    private val bottomSpacing = 0
    private val rightSpacing = 5

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = bottomSpacing
        outRect.top = topSpacing
        outRect.left = leftSpacing
        outRect.right = rightSpacing
    }

}