package io.github.jixiaoyong.wanandroid.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import cf.android666.applibrary.Logger
import com.google.android.material.tabs.TabLayout
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.adapter.MainIndexPagingAdapterOld
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

    private lateinit var viewBinding: FragmentMainSystemBinding

    private val viewModel: SystemViewModel by viewModels { InjectUtils.provideSystemViewModelFactory() }
    private val mainViewModel: MainViewModel by viewModels({ requireActivity() }) { InjectUtils.provideMainViewModelFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentMainSystemBinding.inflate(inflater)

        setupFakeStateBar(viewBinding.stateBarView)

        initView()
        return viewBinding.root
    }

    private fun initView() {
        viewModel.mainTabs.observe(
            viewLifecycleOwner,
            {
                it?.forEachIndexed { index, dataSystemParam ->
                    val tabItem = viewBinding.tabLayout.newTab()
                    tabItem.text = dataSystemParam.name
                    tabItem.tag = index
                    viewBinding.tabLayout.addTab(tabItem)
                }
            }
        )

        viewModel.currentSubTabItems.observe(
            viewLifecycleOwner,
            {
                viewBinding.subTabLayout.removeAllTabs()
                it?.filterNotNull()?.forEachIndexed { index, dataSystemParam ->
                    val tabItem = viewBinding.subTabLayout.newTab()
                    val text = layoutInflater.inflate(R.layout.item_textview, null, false) as TextView
                    text.text = dataSystemParam.name
                    tabItem.tag = index
                    tabItem.customView = text
                    val isSelect = (index == viewModel.currentSubTabIndex.value ?: 0)
                    updateSubTabItemStatus(tabItem.customView as TextView, isSelect)

                    if (isSelect) {
                        tabItem.select()
                    }

                    viewBinding.subTabLayout.addTab(tabItem)
                }
            }
        )

        viewBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
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
        viewBinding.subTabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
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

        val adapter = MainIndexPagingAdapterOld(
            viewModel::updateISystemPostCollectState,
            { itemView, _ ->
                itemView.findViewById<View>(R.id.classTv).visibility = View.GONE
            },
            mainViewModel::isLogin
        )
        viewBinding.postRecyclerView.adapter = adapter
        viewBinding.postRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        viewModel.allSystemPost.observe(viewLifecycleOwner, Observer(adapter::submitList))

        viewBinding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshSubTabsData()
        }

        viewModel.netState.observe(
            viewLifecycleOwner,
            {
                viewBinding.swipeRefreshLayout.isRefreshing = when (it) {
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
        viewBinding.subTabLayout.removeAllTabs()
        val randomCount = random.nextInt(15)
        for (i in 0..randomCount) {
            val tabItem = viewBinding.subTabLayout.newTab()
            val text = layoutInflater.inflate(R.layout.item_textview, null, false) as TextView
            text.text = "subTab $i"
            tabItem.customView = text
            if (i == 0) {
                tabItem.select()
                updateSubTabItemStatus(tabItem.customView as TextView)
            }
            viewBinding.subTabLayout.addTab(tabItem)
        }
    }
}
