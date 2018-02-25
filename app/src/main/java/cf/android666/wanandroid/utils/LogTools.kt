package cf.android666.wanandroid.utils

import android.util.Log

object LogTools{

    var isDebug  = false

    val TAG = "LOG_TOOLS_TAG"

    fun logd(s: String) {
        if (isDebug) {
            Log.d(TAG,"*********************")
            Log.d(TAG,s)
            Log.d(TAG,"*********************")
        }
    }

}