package io.github.jixiaoyong.wanandroid.base

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-14
 * description: todo
 */
open class BaseDialog(context: Context, cancelable: Boolean = false,
                      cancelListener: DialogInterface.OnCancelListener? = null)
    : AlertDialog(context, cancelable, cancelListener)