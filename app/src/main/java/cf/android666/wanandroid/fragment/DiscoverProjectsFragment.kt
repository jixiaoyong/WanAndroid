package cf.android666.wanandroid.fragment

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.adapter.DiscoverProjectsAdapter
import cf.android666.wanandroid.api.WanAndroidApiHelper
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.DiscoverProjectItemBean
import cf.android666.wanandroid.bean.DiscoverProjectTreeBean
import cf.android666.wanandroid.utils.SuperUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_discover_projects.view.*
import java.util.ArrayList

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class DiscoverProjectsFragment : BaseFragment() {

    private var mData  = ArrayList<DiscoverProjectItemBean.DataBean.DatasBean>()

    private var mTreeData = ArrayList<DiscoverProjectTreeBean.DataBean>()

    private var mTabId = 0

    private var mPage = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_discover_projects, container, false)

        for (x in mTreeData.indices) {

            view!!.tab_layout.addTab(view!!.tab_layout.newTab(),x)
            view!!.tab_layout.getTabAt(x)!!.text = mTreeData[x].name
        }

        view!!.tab_layout.tabMode = TabLayout.MODE_SCROLLABLE

        view.tab_layout.setOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                mTabId = tab!!.position

                mPage = 0

                downloadData(1,mTreeData[tab!!.position].id)
            }
        })


        view.recycler_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        view.recycler_view.adapter = DiscoverProjectsAdapter(context, mData, {

            SuperUtil.startActivity(context, ContentActivity::class.java, it)

        }, {

        })


        view.swipe_refresh.setOnRefreshListener{

            downloadData(1,316)

            downloadTree()
        }

        return view
    }


    private fun downloadTree() {

        val observable = WanAndroidApiHelper.getInstance().getProjectTree()

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    mTreeData.clear()

                    mTreeData.addAll(it.data)

                    for (x in mTreeData.indices) {

                        view!!.tab_layout.addTab(view!!.tab_layout.newTab(),x)
                        view!!.tab_layout.getTabAt(x)!!.text = mTreeData[x].name
                    }

                }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        downloadData(1,294)
        downloadTree()

    }

    private fun downloadData(page:Int,cid:Int) {

        val observable = WanAndroidApiHelper.getInstance().getProjectItems(page, cid)

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    mData.clear()

                    mData.addAll(it.data.datas)

                    view!!.recycler_view.adapter.notifyDataSetChanged()

                    view!!.swipe_refresh.isRefreshing = false

                }

    }

}


