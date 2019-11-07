package io.github.jixiaoyong.wanandroid.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.activity.MainActivity
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.view.*

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-06
 * description: 登录页面
 */
class LoginFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        initView(view)
        return view
    }


    private fun initView(view: View) {
        view.registerBtn.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragmentNav)
                    .navigate(R.id.action_loginFragment_to_registerFragment)
        }
        view.skipBtn.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}