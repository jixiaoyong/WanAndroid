package io.github.jixiaoyong.wanandroid.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import cf.android666.applibrary.Logger
import com.google.android.material.tabs.TabLayout
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.adapter.MainIndexPagingAdapter
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import io.github.jixiaoyong.wanandroid.utils.InjectUtils
import io.github.jixiaoyong.wanandroid.utils.NetUtils
import io.github.jixiaoyong.wanandroid.viewmodel.MainViewModel
import io.github.jixiaoyong.wanandroid.viewmodel.SystemViewModel
import kotlinx.android.synthetic.main.fragment_main_system.view.*
import kotlinx.android.synthetic.main.item_main_index_post.view.*
import kotlin.random.Random

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-06
 * description: 体系
 */
class MainSystemFragment : BaseFragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: SystemViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_system, container, false)
        viewModel = ViewModelProviders.of(this,
                InjectUtils.provideSystemViewModelFactory()).get(SystemViewModel::class.java)
        mainViewModel = ViewModelProviders.of(requireActivity(),
                InjectUtils.provideMainViewModelFactory()).get(MainViewModel::class.java)
        setupFakeStateBar(view.stateBarView)

        initView(view)
        return view
    }

    private fun initView(view: View) {

        viewModel.mainTabs.observe(this, Observer {
            it?.forEachIndexed { index, dataSystemParam ->
                val tabItem = view.tabLayout.newTab()
                tabItem.text = dataSystemParam.name
                tabItem.tag = index
                view.tabLayout.addTab(tabItem)
            }
        })

        viewModel.currentSubTabItems.observe(this, Observer {
            view.subTabLayout.removeAllTabs()
            it?.filterNotNull()?.forEachIndexed { index, dataSystemParam ->
                val tabItem = view.subTabLayout.newTab()
                val text = layoutInflater.inflate(R.layout.item_textview, null, false) as TextView
                text.text = dataSystemParam.name
                tabItem.tag = index
                tabItem.customView = text
                val isSelect = (index == viewModel.currentSubTabIndex.value ?: 0)
                updateSubTabItemStatus(tabItem.customView as TextView, isSelect)

                if (isSelect) {
                    tabItem.select()
                }

                view.subTabLayout.addTab(tabItem)
            }
        })


        view.tabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val index = p0?.tag as Int?
                Logger.d("on tabLayout item Click:${index}")

                viewModel.currentMainTabIndex.value = index ?: 0
            }
        })
        view.subTabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                (p0?.customView as? TextView)?.let {
                    updateSubTabItemStatus(it, false)
                }
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                Logger.d("on subTabLayout item Click:${p0?.tag as Int?}")
                viewModel.currentSubTabIndex.value = p0?.tag as Int? ?: 0
                (p0?.customView as? TextView)?.let {
                    updateSubTabItemStatus(it)
                }
            }

        })

        val adapter = MainIndexPagingAdapter(viewModel::updateISystemPostCollectState,
                { itemView, data ->
                    itemView.classTv.visibility = View.GONE
                }, mainViewModel::isLogin)
        view.postRecyclerView.adapter = adapter
        view.postRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        viewModel.allSystemPost.observe(this, Observer(adapter::submitList))

        view.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshSubTabsData()
        }

        viewModel.netState.observe(this, Observer {
            view.swipeRefreshLayout.isRefreshing = when (it) {
                NetUtils.NetworkState.Loading -> true
                else -> false
            }
        })
    }

    private fun updateSubTabItemStatus(it: TextView, isSelect: Boolean = true) {
        it.setTextColor(resources.getColor(if (isSelect) R.color.colorWhite else R.color.colorNormalText))
        it.backgroundTintList = ColorStateList.valueOf(
                resources.getColor(if (isSelect) R.color.colorPrimary else R.color.colorCommonBgGrey))
    }

    private fun refreshSubTab(view: View, random: Random) {
        view.subTabLayout.removeAllTabs()
        val randomCount = random.nextInt(15)
        for (i in 0..randomCount) {
            val tabItem = view.subTabLayout.newTab()
            val text = layoutInflater.inflate(R.layout.item_textview, null, false) as TextView
            text.text = "subTab $i"
            tabItem.customView = text
            if (i == 0) {
                tabItem.select()
                updateSubTabItemStatus(tabItem.customView as TextView)
            }
            view.subTabLayout.addTab(tabItem)
        }
    }

}

