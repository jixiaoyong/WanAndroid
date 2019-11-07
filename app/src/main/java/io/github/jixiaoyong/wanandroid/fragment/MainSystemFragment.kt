package io.github.jixiaoyong.wanandroid.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main_system.view.*
import kotlin.random.Random

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-06
 * description: 体系
 */
class MainSystemFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_system, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        for (i in 0..15) {
            val tabItem = view.tabLayout.newTab()
            tabItem.text = "tab $i"
            view.tabLayout.addTab(tabItem)
        }

        val random = Random(15)
        view.tabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                view.subTabLayout.removeAllTabs()
                val randomCount = random.nextInt(15)
                for (i in 0..randomCount) {
                    val tabItem = view.subTabLayout.newTab()
                    val text = layoutInflater.inflate(R.layout.item_textview, null, false) as TextView
                    text.text = "subTab $i"
                    tabItem.customView = text
                    view.subTabLayout.addTab(tabItem)
                }
            }
        })
        view.subTabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                (p0?.customView as? TextView)?.let {
                    it.setTextColor(resources.getColor(R.color.colorNormalText))
                    it.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorCommonBgGrey))
                }
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                (p0?.customView as? TextView)?.let {
                    it.setTextColor(resources.getColor(R.color.colorWhite))
                    it.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
                }

            }

        })
    }
}

