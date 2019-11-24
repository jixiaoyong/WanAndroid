package io.github.jixiaoyong.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.adapter.MainProjectPagingAdapter
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import io.github.jixiaoyong.wanandroid.utils.InjectUtils
import io.github.jixiaoyong.wanandroid.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_main_project.view.*

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-06
 * description: 项目
 */
class MainProjectFragment : BaseFragment() {

    private lateinit var viewModel: ProjectViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_project, container, false)
        viewModel = ViewModelProviders.of(this,
                InjectUtils.provideProjectViewModelFactory()).get(ProjectViewModel::class.java)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        viewModel.mainTabs.observe(this, Observer {
            it?.forEachIndexed { index, dataProjectParam ->
                val tabItem = view.tabLayout.newTab()
                tabItem.text = dataProjectParam.name
                tabItem.tag = index
                view.tabLayout.addTab(tabItem)
            }
        })

        val adapter = MainProjectPagingAdapter(viewModel::updateIndexPostCollectState)
        view.postRecyclerView.adapter = adapter
        view.postRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        viewModel.allProjectPost.observe(this, Observer {
            view.swipeRefreshLayout.isRefreshing = false
            adapter.submitList(it)
        })

        view.tabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val index = p0?.tag as Int?
                viewModel.currentMainTabIndex.value = index ?: 0
            }
        })

        view.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshProjectList()
        }
    }

}