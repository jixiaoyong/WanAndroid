package io.github.jixiaoyong.wanandroid.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import cf.android666.applibrary.view.BannerViewHelper
import com.bumptech.glide.Glide
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.adapter.MainIndexPagingAdapter
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import io.github.jixiaoyong.wanandroid.utils.InjectUtils
import io.github.jixiaoyong.wanandroid.utils.NetUtils
import io.github.jixiaoyong.wanandroid.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main_index.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-06
 * description: 首页
 */
class MainIndexFragment : BaseFragment() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_index, container, false)
        mainViewModel = ViewModelProviders.of(requireActivity(),
                InjectUtils.provideMainViewModelFactory()).get(MainViewModel::class.java)

        initView(view)
        return view
    }

    private fun initView(view: View) {
        // 在这里初始化recycleView的高度 = 屏幕高度 - bottomNavView
        val rootView = requireActivity().window.decorView.rootView
        rootView.post {
            val listHeight = rootView.height - requireActivity().bottomNavView.height
            val listParams = view.postRecyclerView.layoutParams
            listParams.height = listHeight
            view.postRecyclerView.layoutParams = listParams
        }

        view.nestedScrollView.isNeedInterceptActionMove = this::isNeedIntercept
        view.swipeRefreshLayout.setOnRefreshListener {
            launch {
                withContext(Dispatchers.IO) {
                    mainViewModel.loadIndexPostFromZero()
                }
                view.swipeRefreshLayout.isRefreshing = false
            }
        }

        val postAdapter = MainIndexPagingAdapter(mainViewModel::updateIndexPostCollectState)
        view.postRecyclerView.adapter = postAdapter
        view.postRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        mainViewModel.allIndexPost.observe(this, Observer(postAdapter::submitList))

        mainViewModel.bannerListLiveData.observe(this, Observer {
            it?.let { dataList ->

                val fragments = BannerViewHelper.initImageBannerOf(
                        requireContext(), dataList.size) { imageView, i ->
                    val imgUrl = dataList.getOrNull(i)?.imagePath
                    imageView.setOnClickListener {
                        dataList.getOrNull(i)?.url?.let {
                            NetUtils.loadUrl(requireContext(), it)
                        }
                    }
                    Glide.with(imageView).load(imgUrl).into(imageView)
                }
                view.banner.setViewsAndIndicator(requireFragmentManager(), fragments)
            }
        })

        view.weChatBtn.setOnClickListener {
            goMoreFragment(view, CommonConstants.Action.WECHAT)
        }
        view.favoriteBtn.setOnClickListener {
            goMoreFragment(view, CommonConstants.Action.FAVORITE)
        }
        view.peopleBtn.setOnClickListener {
            goMoreFragment(view, CommonConstants.Action.PEOPLE)
        }
    }

    private fun goMoreFragment(view: View, action: Int) {
        val args = Bundle()
        args.putInt(CommonConstants.Action.KEY, action)
        view.findNavController().navigate(R.id.action_mainIndexFragment_to_moreFragment, args)
    }

    //是否需要拦截postRecyclerView向上滑动
    private fun isNeedIntercept(): Boolean {
        view?.nestedScrollView?.let { targetView ->
            return 0 < (view!!.dividerAfterHotImg!!.y - targetView.scrollY)
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_index, menu)
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
    }

}