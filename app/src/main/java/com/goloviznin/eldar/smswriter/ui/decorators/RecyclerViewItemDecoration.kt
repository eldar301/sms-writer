package com.goloviznin.eldar.smswriter.ui.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemDecoration(private val spaceRect: Rect) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceRect.top
            }
            left =  spaceRect.left
            right = spaceRect.right
            bottom = spaceRect.bottom
        }
    }
}