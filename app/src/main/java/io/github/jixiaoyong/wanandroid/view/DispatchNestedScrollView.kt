package io.github.jixiaoyong.wanandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-11
 * description: todo
 */
class DispatchNestedScrollView @JvmOverloads constructor(context: Context,
                                                         attrs: AttributeSet? = null,
                                                         defStyleAttr: Int = 0)
    : NestedScrollView(context, attrs, defStyleAttr) {


    var isNeedInterceptActionMove: (() -> Boolean)? = null

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (MotionEvent.ACTION_MOVE == ev?.action) {
            return isNeedInterceptActionMove?.invoke() ?: false
        }
        return super.onInterceptTouchEvent(ev)
    }
}