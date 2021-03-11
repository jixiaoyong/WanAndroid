package io.github.jixiaoyong.wanandroid.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import cf.android666.applibrary.Logger
import cf.android666.applibrary.view.Toast
import com.google.android.material.tabs.TabLayout
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.adapter.MainIndexPagingAdapter
import io.github.jixiaoyong.wanandroid.api.bean.DataSystemParam
import io.github.jixiaoyong.wanandroid.databinding.FragmentListBinding
import io.github.jixiaoyong.wanandroid.utils.InjectUtils
import io.github.jixiaoyong.wanandroid.viewmodel.ListViewModel
import io.github.jixiaoyong.wanandroid.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.ArrayList

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-06
 * description:
 */
class ListFragment : Fragment() {

    private lateinit var viewBinding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels { InjectUtils.provideListViewModelFactory() }
    private val mainViewModel: MainViewModel by viewModels({ requireActivity() }) { InjectUtils.provideMainViewModelFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentListBinding.inflate(inflater)

        initView()

        return viewBinding.root
    }

    private fun initView() {
        val adapter = MainIndexPagingAdapter(
            viewModel::updateISystemPostCollectState,
            { itemView, _ ->
                itemView.findViewById<View>(R.id.classTv).visibility = View.GONE
            },
            mainViewModel::isLogin
        )
        viewBinding.postRecyclerView.adapter = adapter
        viewBinding.postRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        val subTabs = arguments?.getParcelableArrayList<DataSystemParam<Any>?>(KEY_CHILDREN)
        subTabs?.filterNotNull()?.forEachIndexed { index, dataSystemParam ->
            val tabItem = viewBinding.subTabLayout.newTab()
            val text = layoutInflater.inflate(R.layout.item_textview, null, false) as TextView
            text.text = dataSystemParam.name
            tabItem.tag = dataSystemParam.id
            tabItem.customView = text
            val isSelect = (index == 0)
            updateSubTabItemStatus(tabItem.customView as TextView, isSelect)

            if (isSelect) {
                tabItem.select()
            }

            viewBinding.subTabLayout.addTab(tabItem)
        }
        subTabs?.first()?.let {
            getSubTabData(it.id, adapter)
        }

        viewBinding.subTabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                (p0?.customView as? TextView)?.let {
                    updateSubTabItemStatus(it, false)
                }
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val cid = p0?.tag as Int? ?: 0
                Logger.d("on subTabLayout item Click:$cid")

                (p0?.customView as? TextView)?.let {
                    updateSubTabItemStatus(it)
                }
                getSubTabData(cid, adapter)
            }
        })

        viewBinding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
        viewBinding.retryButton.setOnClickListener {
            adapter.retry()
        }
        adapter.addLoadStateListener { loadState ->
            viewBinding.postRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            viewBinding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
            viewBinding.swipeRefreshLayout.isRefreshing = loadState.source.refresh is LoadState.Loading

            val errorState = loadState.refresh as? LoadState.Error
                ?: loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.show("\uD83D\uDE28 Wooops ${it.error}")
            }
        }
    }

    private fun getSubTabData(cid: Int, adapter: MainIndexPagingAdapter) {
        lifecycleScope.launch {
            viewModel.getSystemList(cid).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun updateSubTabItemStatus(it: TextView, isSelect: Boolean = true) {
        it.setTextColor(resources.getColor(if (isSelect) R.color.colorWhite else R.color.colorNormalText))
        it.backgroundTintList = ColorStateList.valueOf(
            resources.getColor(if (isSelect) R.color.colorPrimary else R.color.colorCommonBgGrey)
        )
    }

    companion object {

        const val KEY_CHILDREN = "KEY_CHILDREN"

        fun getInstance(children: ArrayList<DataSystemParam<Any>?>): ListFragment {
            val fragment = ListFragment()
            fragment.arguments = Bundle().also {
                it.putParcelableArrayList(KEY_CHILDREN, children)
            }
            return fragment
        }
    }
}
