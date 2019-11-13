package io.github.jixiaoyong.wanandroid.base

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
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
open class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope()

fun Activity.toast(any: Any?) {
    any?.toString()?.let {
        Toast.show(it)
    }
}