package io.github.jixiaoyong.wanandroid.activity

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: 主页面
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavView.setupWithNavController(Navigation.findNavController(this, R.id.fragmentNav))
    }

}