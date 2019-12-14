package io.github.jixiaoyong.wanandroid.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.IBinder
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

/**
 * Created by jixiaoyong1995@gmail.com
 * Data: 2018/11/15.
 * Description: 输入法工具类
 */
object ImmUtils {
    fun hideImm(context: Context, windowToken: IBinder?) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideImm(activity: Activity) {
        try {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm != null) {
                val result1 = imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                if (!result1) {
                    hideKeyboard(activity)
                }
            }
        } catch (e: Exception) {
            hideKeyboard(activity)
        }
    }

    fun hideImm(activity: Activity, dialog: Dialog) {
        try {
            val manager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val result = manager.hideSoftInputFromWindow(dialog.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            hideKeyboard(activity)
        }
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        if (imm != null) {
            val result = imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}