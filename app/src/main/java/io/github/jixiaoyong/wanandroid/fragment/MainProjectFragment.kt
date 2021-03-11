package io.github.jixiaoyong.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.adapter.MainProjectPagingAdapter
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import io.github.jixiaoyong.wanandroid.databinding.FragmentMainProjectBinding
import io.github.jixiaoyong.wanandroid.utils.InjectUtils
import io.github.jixiaoyong.wanandroid.utils.NetUtils
import io.github.jixiaoyong.wanandroid.viewmodel.MainViewModel
import io.github.jixiaoyong.wanandroid.viewmodel.ProjectViewModel

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-06
 * description: 项目
 */
class MainProjectFragment : BaseFragment() {

    private val mainViewModel: MainViewModel by viewModels({ requireActivity() }) { InjectUtils.provideMainViewModelFactory() }
    private val viewModel: ProjectViewModel by viewModels { InjectUtils.provideProjectViewModelFactory() }
    private lateinit var dataBinding: FragmentMainProjectBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_project, container, false)
        setupFakeStateBar(dataBinding.stateBarView)

        initView()
        return dataBinding.root
    }

    private fun initView() {
        viewModel.mainTabs.observe(
            viewLifecycleOwner,
            {
                it?.forEachIndexed { index, dataProjectParam ->
                    val tabItem = dataBinding.tabLayout.newTab()
                    tabItem.text = dataProjectParam.name
                    tabItem.tag = index
                    dataBinding.tabLayout.addTab(tabItem)
                }
            }
        )

        val adapter = MainProjectPagingAdapter(
            viewModel::updateIndexPostCollectState, isLogin = mainViewModel::isLogin
        )
        dataBinding.postRecyclerView.adapter = adapter
        dataBinding.postRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        viewModel.allProjectPost.observe(viewLifecycleOwner, Observer(adapter::submitList))

        dataBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {}

            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val index = p0?.tag as Int?
                viewModel.currentMainTabIndex.value = index ?: 0
            }
        })

        dataBinding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshProjectList()
        }

        viewModel.netState.observe(
            viewLifecycleOwner,
            {
                dataBinding.swipeRefreshLayout.isRefreshing = when (it) {
                    NetUtils.NetworkState.Loading -> true
                    else -> false
                }
            }
        )
    }
}
