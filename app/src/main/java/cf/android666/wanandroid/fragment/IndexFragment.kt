package cf.android666.wanandroid.fragment

import android.os.Bundle
import android.support.design.widget.TabItem
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.adapter.MFragmentViewPagerAdapter
import cf.android666.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_index.*
import kotlinx.android.synthetic.main.fragment_index.view.*

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class IndexFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_index, container, false)

        val fragments = arrayListOf<BaseFragment>()

        fragments.add(IndexPostFragment())
        fragments.add(IndexFavoriteFragment())

//        view.viewpager.adapter = MFragmentViewPagerAdapter(activity.supportFragmentManager, fragments)
        view.viewpager.adapter = MFragmentViewPagerAdapter(childFragmentManager, fragments)


        var tablayout = view.tab_layout
        //设置该方法后会删除tab标题，因此在后面手动加上
        tablayout.setupWithViewPager(view.viewpager)
        tablayout.getTabAt(0)!!.text = "主页"
        tablayout.getTabAt(1)!!.text = "收藏"



        return view
    }
}