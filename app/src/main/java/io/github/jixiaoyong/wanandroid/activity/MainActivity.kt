package io.github.jixiaoyong.wanandroid.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.base.BaseActivity
import io.github.jixiaoyong.wanandroid.databinding.ActivityMainBinding
import io.github.jixiaoyong.wanandroid.utils.BottomNabControl
import io.github.jixiaoyong.wanandroid.utils.InjectUtils
import io.github.jixiaoyong.wanandroid.viewmodel.MainViewModel

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: 主页面
 */
class MainActivity : BaseActivity(), BottomNabControl {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        ViewModelProviders.of(
            this,
            InjectUtils.provideMainViewModelFactory()
        ).get(MainViewModel::class.java)

        binding.bottomNavView.setupWithNavController(Navigation.findNavController(this, R.id.fragmentNav))
    }

    override fun changBottomNavViewVisibility(isVisible: Boolean) {
        binding.bottomNavView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun getBottomNavViewHeight() = binding.bottomNavView.height
}
