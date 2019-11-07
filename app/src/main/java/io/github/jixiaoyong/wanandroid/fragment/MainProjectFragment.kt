package io.github.jixiaoyong.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main_project.view.*

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-06
 * description: 项目
 */
class MainProjectFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_project, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        for (i in 0..15) {
            val tabItem = view.tabLayout.newTab()
            tabItem.text = "tab $i"
            view.tabLayout.addTab(tabItem)
        }

    }
}