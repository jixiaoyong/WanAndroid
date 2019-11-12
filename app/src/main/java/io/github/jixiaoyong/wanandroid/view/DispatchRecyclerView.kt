package io.github.jixiaoyong.wanandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.core.widget.NestedScrollView

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-07
 * description: todo
 */
class InterceptScrollView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null)
    : NestedScrollView(context, attributeSet) {

    private var lastPoint = Pair(0.0f, 0.0f)


    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastPoint = Pair(event.rawX, event.rawY)
            }
            MotionEvent.ACTION_MOVE -> {
                val dy = event.rawY - lastPoint.second
                if (dy < 0 && Math.abs(dy) > ViewConfiguration.get(context).scaledTouchSlop
                        && Math.abs(event.rawX - lastPoint.first) < Math.abs(dy)) {
                    //scroll to up
                    isNeedIntercept?.let {
                        if (it()) {
                            return true
                        }
                    }
                }
                lastPoint = Pair(event.rawX, event.rawY)
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        return super.onInterceptTouchEvent(event)
    }


    fun setNeedInterceptTouchEventFun(func: (() -> Boolean)?) {
        isNeedIntercept = func
    }

    var isNeedIntercept: (() -> Boolean)? = null
}