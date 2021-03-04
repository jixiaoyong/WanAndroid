package io.github.jixiaoyong.wanandroid.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import cf.android666.applibrary.Logger
import com.google.android.material.tabs.TabLayout
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.adapter.MainIndexPagingAdapter
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import io.github.jixiaoyong.wanandroid.databinding.FragmentMainSystemBinding
import io.github.jixiaoyong.wanandroid.utils.InjectUtils
import io.github.jixiaoyong.wanandroid.utils.NetUtils
import io.github.jixiaoyong.wanandroid.viewmodel.MainViewModel
import io.github.jixiaoyong.wanandroid.viewmodel.SystemViewModel
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
    private lateinit var dataBinding: FragmentMainSystemBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_system, container, false)
        val view = dataBinding.root

        viewModel = ViewModelProviders.of(
            this,
            InjectUtils.provideSystemViewModelFactory()
        ).get(SystemViewModel::class.java)
        mainViewModel = ViewModelProviders.of(
            requireActivity(),
            InjectUtils.provideMainViewModelFactory()
        ).get(MainViewModel::class.java)
        setupFakeStateBar(dataBinding.stateBarView)

        initView(view)
        return view
    }

    private fun initView(view: View) {

        viewModel.mainTabs.observe(
            viewLifecycleOwner,
            Observer {
                it?.forEachIndexed { index, dataSystemParam ->
                    val tabItem = dataBinding.tabLayout.newTab()
                    tabItem.text = dataSystemParam.name
                    tabItem.tag = index
                    dataBinding.tabLayout.addTab(tabItem)
                }
            }
        )

        viewModel.currentSubTabItems.observe(
            viewLifecycleOwner,
            Observer {
                dataBinding.subTabLayout.removeAllTabs()
                it?.filterNotNull()?.forEachIndexed { index, dataSystemParam ->
                    val tabItem = dataBinding.subTabLayout.newTab()
                    val text = layoutInflater.inflate(R.layout.item_textview, null, false) as TextView
                    text.text = dataSystemParam.name
                    tabItem.tag = index
                    tabItem.customView = text
                    val isSelect = (index == viewModel.currentSubTabIndex.value ?: 0)
                    updateSubTabItemStatus(tabItem.customView as TextView, isSelect)

                    if (isSelect) {
                        tabItem.select()
                    }

                    dataBinding.subTabLayout.addTab(tabItem)
                }
            }
        )

        dataBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val index = p0?.tag as Int?
                Logger.d("on tabLayout item Click:$index")

                viewModel.currentMainTabIndex.value = index ?: 0
            }
        })
        dataBinding.subTabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
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

        val adapter = MainIndexPagingAdapter(
            viewModel::updateISystemPostCollectState,
            { itemView, data ->
                itemView.findViewById<View>(R.id.classTv).visibility = View.GONE
            },
            mainViewModel::isLogin
        )
        dataBinding.postRecyclerView.adapter = adapter
        dataBinding.postRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        viewModel.allSystemPost.observe(viewLifecycleOwner, Observer(adapter::submitList))

        dataBinding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshSubTabsData()
        }

        viewModel.netState.observe(
            viewLifecycleOwner,
            Observer {
                dataBinding.swipeRefreshLayout.isRefreshing = when (it) {
                    NetUtils.NetworkState.Loading -> true
                    else -> false
                }
            }
        )
    }

    private fun updateSubTabItemStatus(it: TextView, isSelect: Boolean = true) {
        it.setTextColor(resources.getColor(if (isSelect) R.color.colorWhite else R.color.colorNormalText))
        it.backgroundTintList = ColorStateList.valueOf(
            resources.getColor(if (isSelect) R.color.colorPrimary else R.color.colorCommonBgGrey)
        )
    }

    private fun refreshSubTab(view: View, random: Random) {
        dataBinding.subTabLayout.removeAllTabs()
        val randomCount = random.nextInt(15)
        for (i in 0..randomCount) {
            val tabItem = dataBinding.subTabLayout.newTab()
            val text = layoutInflater.inflate(R.layout.item_textview, null, false) as TextView
            text.text = "subTab $i"
            tabItem.customView = text
            if (i == 0) {
                tabItem.select()
                updateSubTabItemStatus(tabItem.customView as TextView)
            }
            dataBinding.subTabLayout.addTab(tabItem)
        }
    }
}
