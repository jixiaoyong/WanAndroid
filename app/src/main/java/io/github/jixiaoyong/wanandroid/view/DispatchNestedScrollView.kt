package io.github.jixiaoyong.wanandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView
import kotlin.math.abs

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

    /**
     * 判断是否拦截子View点击事件的方法，由外部实现
     */
    var isNeedInterceptActionMove: ((ev: MotionEvent?, direction: Int) -> Boolean)? = null

    private var moveDirection = Direction.NONE
    private var lastPoint = Pair(0F, 0F)

    /**
     * 是否拦截子View的点击事件
     * 返回true则拦截，否则不拦截
     */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {

        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastPoint = Pair(ev.rawX, ev.rawY)
            }
            MotionEvent.ACTION_MOVE -> {
                val currentPoint = Pair(ev.rawX, ev.rawY)
                moveDirection = if (abs(currentPoint.first - lastPoint.first) >
                        (abs(currentPoint.second - lastPoint.second))) {
                    Direction.HORIZONTAL
                } else {
                    Direction.VERTICAL
                }
                lastPoint = Pair(ev.rawX, ev.rawY)
                return isNeedInterceptActionMove?.invoke(ev, moveDirection) ?: false
            }
        }

        return super.onInterceptTouchEvent(ev)
    }

    companion object {

        object Direction {
            const val NONE = -1
            const val VERTICAL = 0
            const val HORIZONTAL = 1
        }
    }
}