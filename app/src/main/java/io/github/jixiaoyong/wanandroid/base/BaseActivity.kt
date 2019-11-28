package io.github.jixiaoyong.wanandroid.base

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cf.android666.applibrary.Logger
import cf.android666.applibrary.utils.ImmersiveUtils
import cf.android666.applibrary.view.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel


/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: todo
 */
open class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersiveUtils.setTransparentStateBar(window)

        Logger.d("Activity($this) onCreate")
    }

    override fun onResume() {
        super.onResume()
        Logger.d("Activity($this) onResume")
    }

    override fun onPause() {
        super.onPause()
        Logger.d("Activity($this) onCreatePause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("Activity($this) onDestroy")
        cancel()
    }
}

fun Activity.toast(any: Any?) {
    any?.toString()?.let {
        Toast.show(it)
    }
}