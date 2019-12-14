package io.github.jixiaoyong.wanandroid.utils

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.view.ViewCompat
import java.net.URLDecoder


/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: todo
 */
object Utils {

    fun translucentStatusBar(activity: Activity, hideStatusBarBackground: Boolean) {
        val window = activity.window
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (hideStatusBarBackground) {
            //如果为全透明模式，取消设置Window半透明的Flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //设置状态栏为透明
            window.statusBarColor = Color.TRANSPARENT
            //设置window的状态栏不可见
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else {
            //如果为半透明模式，添加设置Window半透明的Flag
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //设置系统状态栏处于可见状态
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }

        //view不根据系统窗口来调整自己的布局
        val mContentView = window.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
        val mChildView = mContentView.getChildAt(0)
        if (mChildView != null) {
            mChildView.fitsSystemWindows = true
            ViewCompat.requestApplyInsets(mChildView)
        }
    }

    /**
     * 最长不得超过7个汉字，或50个字节(数字，字母和下划线)
     */
    fun isNameCorrect(name: String?): Boolean {
        val name = name?.trim()
        return if (!name.isNullOrBlank() && name.length > 1) {
            val chineseRegex = Regex("[\\u4e00-\\u9fa5]*")
            val numLetterRegex = Regex("[\\u4e00-\\u9fa5a-zA-Z0-9_@.]+")

            if (chineseRegex.matches(name) && name.length <= 7) {
                true
            } else numLetterRegex.matches(name) && (name.length <= 50)
        } else {
            false
        }
    }

    /**
     * 密码6~50位且为数字、字母、-、_
     */
    fun isPasswordCorrect(pwd: String?): Boolean {
        val newPwd = pwd?.trim()
        return if (!newPwd.isNullOrEmpty() && (newPwd.length in 6..50)) {
            val numLetterRegex = Regex("[a-zA-Z0-9_]*")
            numLetterRegex.matches(newPwd)
        } else {
            false
        }
    }

    /**
     * 转义URL中的特殊字符
     */
    fun decodeUrlStr(url: String, enc: String = Charsets.UTF_8.name()): String? {
        return URLDecoder.decode(url, enc)
    }

    /**
     * 指定坐标([x],[y])是否在[view]的范围内
     */
    fun isInViewScope(view: View, x: Float, y: Float): Boolean {
        val locationInfo = IntArray(2)
        view.getLocationOnScreen(locationInfo)
        //(left,right,top,bottom)
        val left = locationInfo[0]
        val right = left + view.measuredWidth
        val top = locationInfo[1]
        val bottom = top + view.measuredHeight

        return x.toInt() in left..right && y.toInt() in top..bottom
    }

}
