package io.github.jixiaoyong.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import io.github.jixiaoyong.wanandroid.databinding.FragmentMainTodoBinding

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-06
 * description: 中间TODO页面
 */
class MainTodoFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentMainTodoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = FragmentMainTodoBinding.inflate(inflater)
        val view = dataBinding.root
        setupFakeStateBar(dataBinding.stateBarView)

        initView(view)
        return view
    }

    private fun initView(view: View) {
    }
}
