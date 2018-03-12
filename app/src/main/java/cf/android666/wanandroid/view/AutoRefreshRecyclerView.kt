package cf.android666.wanandroid.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import android.view.MotionEvent
import com.orhanobut.logger.Logger

/**
 * Created by jixiaoyong on 2018/3/13.
 * email:jixiaoyong1995@gmail.com
 */
class AutoRefreshRecyclerView(context: Context, attributeSet: AttributeSet?, defStyle: Int)
    : RecyclerView(context, attributeSet, defStyle) {

    private var onFootListener:(() -> Unit)? = null

    constructor(context: Context):this(context,null,0)

    constructor(context: Context,attributeSet: AttributeSet):this(context,attributeSet,0)

    private var oldY = 0f

    override fun onTouchEvent(e: MotionEvent?): Boolean {

        when (e!!.action) {

            MotionEvent.ACTION_DOWN -> oldY = e.y

            MotionEvent.ACTION_UP -> {

                Logger.wtf("onTouchEvent e.y is ${e.y}")

                checkFoot(e.y - oldY)

            }
        }

        return super.onTouchEvent(e)
    }

    private fun checkFoot(dy: Float) {

        var lastPosition = 0

        when(layoutManager){

            is LinearLayoutManager ->
                lastPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            is StaggeredGridLayoutManager ->
                lastPosition = (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(IntArray(2))[1]

        }

        Logger.wtf("dy is $dy")

        if (dy < 0 && lastPosition > childCount - 2 && onFootListener!=null) {
            onFootListener!!.invoke()
            Logger.wtf("onFootListener!!.invoke() ")

        }

    }

    fun setOnFootListener(listener:( () -> Unit) ){
        onFootListener = listener
    }

}