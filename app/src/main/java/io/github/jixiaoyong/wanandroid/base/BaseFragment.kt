package io.github.jixiaoyong.wanandroid.base

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import cf.android666.applibrary.Logger
import cf.android666.applibrary.view.Toast
import io.github.jixiaoyong.wanandroid.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: todo
 */
open class BaseFragment : Fragment(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("Fragment($this) onCreate")
    }

    override fun onResume() {
        super.onResume()
        Logger.d("Fragment($this) onResume")
    }

    override fun onPause() {
        super.onPause()
        Logger.d("Fragment($this) onCreatePause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("Fragment($this) onDestroy")
    }

    protected fun getImmersiveStateBar(colorId: Int = R.color.colorCollected) {
        val statusBarHeight: Int = getStateBarHeight()

        val immersiveStateBar = View(requireContext())
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, statusBarHeight)
        immersiveStateBar.layoutParams = lp
        immersiveStateBar.setBackgroundColor(resources.getColor(colorId))
    }

    private fun getStateBarHeight(): Int {
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    protected fun setupFakeStateBar(stateBarView: View) {
        val lp = stateBarView.layoutParams
        lp.height = getStateBarHeight()
        stateBarView.layoutParams = lp
    }
}

fun Fragment.toast(any: Any?) {
    if (any is Int) {
        Toast.show(requireContext().getString(any))
    } else {
        any?.toString()?.let {
            Toast.show(it)
        }
    }

}