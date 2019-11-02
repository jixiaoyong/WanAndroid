package cf.android666.wanandroid.adapter

import cf.android666.wanandroid.base.BaseFragment

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class MFragmentViewPagerAdapter(fragmentManager: androidx.fragment.app.FragmentManager,
                                private val fragments:ArrayList<BaseFragment>)
    : androidx.fragment.app.FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

}