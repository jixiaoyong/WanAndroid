package cf.android666.wanandroid.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cf.android666.wanandroid.base.BaseFragment

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class MFragmentViewPagerAdapter(frgamentManager:FragmentManager,fragments:ArrayList<BaseFragment>)
    : FragmentPagerAdapter(frgamentManager) {

    private var list = arrayListOf<BaseFragment>()

    init {
        list = fragments
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

}