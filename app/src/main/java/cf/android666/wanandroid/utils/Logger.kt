package cf.android666.wanandroid.utils

import android.util.Log

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: www.jixiaoyong.github.io
 * date: 2019/1/19
 * description: todo
 */

object Logger {

    @JvmStatic
    var isLog = true

    @JvmStatic
    fun generateTag(): String {
        val stack = Thread.currentThread().stackTrace[3]
        return "${stack.className}.${stack.methodName}(Line:${stack.lineNumber})"
    }

    fun d(any: Any) {
        if (isLog) {
            Log.d(generateTag(), any.toString())
        }
    }

    fun e(any: Any) {
        if (isLog) {
            Log.e(generateTag(), any.toString())
        }
    }

    fun i(any: Any) {
        if (isLog) {
            Log.i(generateTag(), any.toString())
        }
    }
}