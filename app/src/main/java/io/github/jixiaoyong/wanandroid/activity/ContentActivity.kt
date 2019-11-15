package io.github.jixiaoyong.wanandroid.activity

import android.os.Bundle
import android.view.Menu
import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.base.BaseActivity
import io.github.jixiaoyong.wanandroid.utils.CommonConstants

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: 文章查看页面
 */
class ContentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val url = intent.getStringExtra(CommonConstants.ACTION_URL)
        Logger.d("url:$url")
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_content_share, menu)
        return super.onCreateOptionsMenu(menu)
    }
}