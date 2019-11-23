package io.github.jixiaoyong.wanandroid.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.databinding.BindingAdapter
import cf.android666.applibrary.Logger
import com.bumptech.glide.Glide
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.api.ApiCommondConstants
import io.github.jixiaoyong.wanandroid.api.bean.DataCoinParam
import io.github.jixiaoyong.wanandroid.data.CookiesBean
import io.github.jixiaoyong.wanandroid.utils.Utils

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


@BindingAdapter("userName")
fun bindUserName(view: TextView, cookies: List<CookiesBean>?) {
    val name = cookies?.getOrNull(0)?.loginUserName
    name?.let {
        view.text = Utils.decodeUrlStr(it)
    }
}

@BindingAdapter("coinInfo")
fun bindCoinInfo(view: TextView, coinInfo: DataCoinParam?) {
    val name = coinInfo?.coinCount
    name?.let {
        view.text = "$it " + view.context.getString(R.string.coin)
    }
}

@BindingAdapter("app:isShow")
fun bindIsVisibleView(view: View, isShow: Boolean) {
    view.visibility = if (isShow) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("app:isWechat")
fun bindIsWechat(view: View, superChapterId: Int) {
    view.visibility = if (superChapterId == ApiCommondConstants.SuperChapterId.WECHAT) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("app:isQuestion")
fun bindIsQuestion(view: View, superChapterId: Int) {
    view.visibility = if (superChapterId == ApiCommondConstants.SuperChapterId.QUESTION) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("app:isNew")
fun bindIsNew(view: View, fresh: Boolean) {
    view.visibility = if (fresh) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("app:isFavorite", "app:title")
fun bindIsFavorite(view: TextView, isFavorite: Boolean, title: String) {
    Logger.d("Title:$title,isF:$isFavorite")

    val (text, color) = if (isFavorite) {
        Pair(R.string.collected_yes, R.color.colorCollected)
    } else {
        Pair(R.string.collected_no, R.color.colorGreenText)
    }
    view.text = view.context.getString(text)
    view.setTextColor(view.context.resources.getColor(color))
}

@BindingAdapter("app:chapterName", "app:superChapterName")
fun bindChapterName(view: TextView, chapterName: String?, superChapterName: String?) {
    view.text = if (!superChapterName.isNullOrEmpty() && !chapterName.isNullOrEmpty()) {
        "$chapterName/$superChapterName"
    } else if (!superChapterName.isNullOrEmpty()) {
        "$superChapterName"
    } else if (!chapterName.isNullOrEmpty()) {
        "$chapterName"
    } else {
        ""
    }
}

@BindingAdapter("app:isShow")
fun bindIsVisible(view: Group, isShow: Boolean) {
    view.visibility = if (isShow) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("imgUrl")
fun bindIsVisible(view: ImageView, imgUrl: String) {
    view.visibility = if (imgUrl.isNotBlank()) {
        Glide.with(view).load(imgUrl).into(view)
        View.VISIBLE
    } else {
        View.GONE
    }
}