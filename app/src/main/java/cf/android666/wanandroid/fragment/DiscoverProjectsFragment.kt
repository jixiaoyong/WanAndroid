package cf.android666.wanandroid.fragment

import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cf.android666.mylibrary.view.SwitchStateLayout
import cf.android666.wanandroid.R
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.adapter.DiscoverProjectsAdapter
import cf.android666.wanandroid.api.cookie.CookieTools
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.BaseArticlesBean
import cf.android666.wanandroid.bean.DiscoverProjectTreeBean
import cf.android666.wanandroid.utils.SuperUtil
import com.google.android.material.tabs.TabLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_discover_projects.view.*
import java.util.*

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class DiscoverProjectsFragment : BaseFragment() {

    private var mData = ArrayList<BaseArticlesBean>()
    private var mTreeData = ArrayList<DiscoverProjectTreeBean.DataBean>()
    private var mTabId = 0
    private var mPage = 0
    private var currentPage = 0
    private var pageCount = 0
    override var layoutId = R.layout.fragment_discover_projects

    override fun onCreateViewState(savedInstanceState: Bundle?) {
        mView!!.switch_state.showView(SwitchStateLayout.VIEW_EMPTY)
        for (x in mTreeData.indices) {
            mView!!.tab_layout.addTab(mView!!.tab_layout.newTab(), x)
            mView!!.tab_layout.getTabAt(x)!!.text = mTreeData[x].name
        }

        mView!!.tab_layout.tabMode = TabLayout.MODE_SCROLLABLE
        mView!!.tab_layout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                mTabId = tab!!.position
                mPage = 0
                downloadData(1, mTreeData[tab.position].id)
            }
        })


        mView!!.recycler_view.layoutManager = StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL)
        mView!!.recycler_view.adapter = DiscoverProjectsAdapter(this, mData, {
            SuperUtil.startActivity(requireContext(), ContentActivity::class.java, it)
        }, {})

        mView!!.recycler_view.setOnFootListener {
            if (currentPage < pageCount) {
                downloadData(++currentPage, mTreeData[mTabId].id)
            }
        }

        mView!!.swipe_refresh.setOnRefreshListener {
            downloadData(1, 316)
            downloadTree()
        }

    }

    override fun lazyLoadData() {
        downloadData(1, 294)
        downloadTree()
    }

    private fun downloadTree() {

        CookieTools.getCookieService()!!
                .getProjectTree()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    mTreeData.clear()

                    mTreeData.addAll(it.data)

                    for (x in mTreeData.indices) {

                        view!!.tab_layout.addTab(view!!.tab_layout.newTab(), x)
                        view!!.tab_layout.getTabAt(x)!!.text = mTreeData[x].name
                    }
                }, {
                    mView!!.switch_state.showView(SwitchStateLayout.VIEW_ERROR)
                }, {
                    mView!!.switch_state.showContentView()
                })
    }

    private fun downloadData(page: Int, cid: Int) {

        CookieTools.getCookieService()!!
                .getProjectItems(page, cid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (page) {
                        1 -> {
                            mData.clear();mData.addAll(it.data.datas)
                        }
                        else -> mData.addAll(it.data.datas)
                    }

                    pageCount = it.data.pageCount
                    currentPage = it.data.curPage
                    view!!.recycler_view.adapter?.notifyDataSetChanged()
                    view!!.swipe_refresh.isRefreshing = false
                }, {
                    mView!!.switch_state.showView(SwitchStateLayout.VIEW_ERROR)
                }, {
                    mView!!.switch_state.showContentView()
                })

    }

}


