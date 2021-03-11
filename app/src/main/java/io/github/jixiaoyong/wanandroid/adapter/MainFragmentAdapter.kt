package io.github.jixiaoyong.wanandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.jixiaoyong.wanandroid.api.bean.DataSystemParam
import io.github.jixiaoyong.wanandroid.fragment.ListFragment

/**
 * description ： TODO
 * @author : jixiaoyong
 * @email : jixiaoyong1995@gmail.com
 * @date : 2021/3/11
 */
class MainFragmentAdapter(
    fragmentActivity: FragmentActivity,
    private val fragmentList: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]
}

class ListFragmentAdapter(
    private val mainTabs: List<DataSystemParam<DataSystemParam<Any>>>,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = mainTabs.size

    override fun createFragment(position: Int) = ListFragment.getInstance(ArrayList(mainTabs.get(position).children))
}
