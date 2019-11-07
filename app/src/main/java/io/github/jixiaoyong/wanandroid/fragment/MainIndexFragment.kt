package io.github.jixiaoyong.wanandroid.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.adapter.MainIndexPostAdapter
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main_index.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-06
 * description: 首页
 */
class MainIndexFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_index, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        //todo 在这里初始化recycleView的高度 = 屏幕高度 - bottomNavView - hotTitle
        view.postRecyclerView.adapter = MainIndexPostAdapter()
        val random = Random(120)
        view.swipeRefreshLayout.setOnRefreshListener {
            launch {
                delay(3_000)
                val count = random.nextInt(30, 300)
                view.postRecyclerView.adapter = MainIndexPostAdapter(count)
                view.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_index, menu)
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
    }
}