package cf.android666.wanandroid.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cf.android666.wanandroid.base.BaseFragment

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class MFragmentViewPagerAdapter(fragmentManager:FragmentManager,
                                private val fragments:ArrayList<BaseFragment>)
    : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

}