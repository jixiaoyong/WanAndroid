package cf.android666.wanandroid.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

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

                checkFoot(e.y - oldY)

            }
        }

        return super.onTouchEvent(e)
    }

    private fun checkFoot(dy: Float) {

        var lastPosition = 0

        when(layoutManager){

            is androidx.recyclerview.widget.LinearLayoutManager ->
                lastPosition = (layoutManager as androidx.recyclerview.widget.LinearLayoutManager).findLastVisibleItemPosition()

            is androidx.recyclerview.widget.StaggeredGridLayoutManager ->
                lastPosition = (layoutManager as androidx.recyclerview.widget.StaggeredGridLayoutManager).findLastVisibleItemPositions(IntArray(2))[1]

        }


        if (dy < 0 && lastPosition > childCount - 2
                && onFootListener!=null) {

            onFootListener!!.invoke()

        }

    }

    fun setOnFootListener(listener:( () -> Unit) ){
        onFootListener = listener
        Log.d("TAG","on foot recycler...")
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)

    }

}