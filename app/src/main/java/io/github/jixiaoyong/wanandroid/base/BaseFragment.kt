package io.github.jixiaoyong.wanandroid.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import cf.android666.applibrary.Logger
import cf.android666.applibrary.view.Toast
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