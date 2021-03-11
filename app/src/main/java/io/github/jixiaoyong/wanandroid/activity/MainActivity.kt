package io.github.jixiaoyong.wanandroid.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.adapter.MainFragmentAdapter
import io.github.jixiaoyong.wanandroid.base.BaseActivity
import io.github.jixiaoyong.wanandroid.databinding.ActivityMainBinding
import io.github.jixiaoyong.wanandroid.fragment.*
import io.github.jixiaoyong.wanandroid.utils.BottomNabControl

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: 主页面
 */
class MainActivity : BaseActivity(), BottomNabControl {

    private lateinit var binding: ActivityMainBinding
    private val menuIds = arrayOf(
        R.id.mainIndexFragment, R.id.mainSystemFragment,
        /* R.id.mainTodoFragment,*/ R.id.mainProjectFragment, R.id.mainAboutFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.pager.adapter = MainFragmentAdapter(
            this@MainActivity,
            arrayListOf(
                MainIndexFragment(),
                MainSystemFragment(),
//                MainTodoFragment(),
                MainProjectFragment(),
                MainAboutFragment()
            )
        )
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavView.selectedItemId = menuIds.get(position)
            }
        })
        binding.pager.isUserInputEnabled = false
        binding.bottomNavView.setOnNavigationItemSelectedListener { menu ->
            val pageId = menuIds.indexOf(menu.itemId)
            binding.pager.setCurrentItem(pageId, false)
            true
        }
    }

    override fun changBottomNavViewVisibility(isVisible: Boolean) {
        binding.bottomNavView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun getBottomNavViewHeight() = binding.bottomNavView.height
}
