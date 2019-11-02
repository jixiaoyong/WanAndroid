package cf.android666.wanandroid.fragment

import android.os.Bundle
import cf.android666.wanandroid.R
import cf.android666.wanandroid.adapter.MFragmentViewPagerAdapter
import cf.android666.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_index.view.*

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class IndexFragment : BaseFragment() {

    override var layoutId = R.layout.fragment_index

    override fun onCreateViewState(savedInstanceState: Bundle?) {
        val fragments = arrayListOf<BaseFragment>()

        fragments.add(IndexPostFragment())
        fragments.add(IndexFavoriteFragment())

        mView!!.viewpager.adapter = MFragmentViewPagerAdapter(childFragmentManager, fragments)

        var tablayout = mView!!.tab_layout

        //设置该方法后会删除tab标题，因此在后面手动加上
        tablayout.setupWithViewPager(mView!!.viewpager)
        tablayout.getTabAt(0)!!.text = "主页"
        tablayout.getTabAt(1)!!.text = "收藏"

        tablayout.animation = null
    }
}