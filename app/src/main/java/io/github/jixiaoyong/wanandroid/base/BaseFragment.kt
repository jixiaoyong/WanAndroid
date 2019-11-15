package io.github.jixiaoyong.wanandroid.base

import androidx.fragment.app.Fragment
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
open class BaseFragment : Fragment(), CoroutineScope by MainScope()

fun Fragment.toast(any: Any?) {
    if (any is Int) {
        Toast.show(requireContext().getString(any))
    } else {
        any?.toString()?.let {
            Toast.show(it)
        }
    }

}