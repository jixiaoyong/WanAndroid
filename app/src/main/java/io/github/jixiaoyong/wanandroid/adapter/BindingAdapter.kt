package io.github.jixiaoyong.wanandroid.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.github.jixiaoyong.wanandroid.R

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-13
 * description: todo
 */

@BindingAdapter("hideIfLogin")
fun bindHideIfLogin(view: View, isLogin: Boolean) {
    view.visibility = if (isLogin) View.GONE else View.VISIBLE
}

@BindingAdapter("loginBtnText")
fun bindLoginBtnText(view: Button, isLogin: Boolean) {
    val context = view.context
    view.text = context.resources.getString(if (isLogin) R.string.login else R.string.register)
}

@BindingAdapter("hintText")
fun bindHintText(view: TextView, isLogin: Boolean) {
    val context = view.context
    view.text = context.resources.getString(if (isLogin) R.string.tip_no_account else R.string.tip_already_account)
}

@BindingAdapter("changeStateText")
fun bindChangeStateText(view: TextView, isLogin: Boolean) {
    val context = view.context
    view.text = context.resources.getString(if (isLogin) R.string.create_account else R.string.go_login)
}