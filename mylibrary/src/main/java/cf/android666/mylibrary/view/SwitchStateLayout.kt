package cf.android666.mylibrary.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import cf.android666.mylibrary.R
import java.util.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
/**
 * Created by jixiaoyong on 2018/3/16.
 * email:jixiaoyong1995@gmail.com
 */
class SwitchStateLayout(context: Context, attrs: AttributeSet?, defStyle: Int, defRes: Int)
    : FrameLayout(context, attrs, defStyle, defRes) {

    constructor (context: Context, attrs: AttributeSet, defStyle: Int)
            : this(context, attrs, defStyle, 0)

    constructor (context: Context, attrs: AttributeSet) : this(context, attrs, 0, 0)

    constructor (context: Context)
            : this(context, null, 0, 0)

    private var layouts: IntArray = intArrayOf(
            R.layout.layout_loading,
            R.layout.layout_empty,
            R.layout.layout_error)

    var currentView: Int = -1

    companion object {

        const val VIEW_LOADING = 0

        const val VIEW_EMPTY = 1

        const val VIEW_ERROR = 2
    }

    var views: ArrayList<View> = arrayListOf()

    private var inflater: LayoutInflater? = null

    init {

        inflater = LayoutInflater.from(context)

        setViews(layouts)

    }

    fun showView(viewNum: Int) {

        if (currentView < 0 && currentView >= views.size){

            throw ArrayIndexOutOfBoundsException("the index of view($viewNum) " +
                    "is OutOfBounds(which should be 0~${views.size-1})!")
        }

        currentView = viewNum

        showContentView()

        addView(views[viewNum])

    }

    fun getCurrentView(): View {

        return if (currentView == -1) {
            this
        }else if (currentView > -1 && currentView < views.size) {
            views[currentView]
        } else {
            throw ArrayIndexOutOfBoundsException("the index of view($currentView) " +
                    "is OutOfBounds(which should be 0~${views.size-1})!")
        }

    }

    fun showContentView() {

        for (x in 0 until childCount) {

            var child = getChildAt(x)

            if (views.contains(child)) {

                removeView(child)

            }
        }
    }

    fun setViews(list: IntArray) {

        views.clear()

        for (x in list.iterator()) {

            views.add(inflater?.inflate(x, this, false)!!)

        }
    }

    fun getAllViews(): ArrayList<View> = views
}