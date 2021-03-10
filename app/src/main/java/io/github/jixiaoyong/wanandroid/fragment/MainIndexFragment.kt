package io.github.jixiaoyong.wanandroid.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import cf.android666.applibrary.Logger
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.adapter.MainIndexPagingAdapter
import io.github.jixiaoyong.wanandroid.adapter.SearchAdapter
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import io.github.jixiaoyong.wanandroid.base.toast
import io.github.jixiaoyong.wanandroid.databinding.FragmentMainIndexBinding
import io.github.jixiaoyong.wanandroid.utils.*
import io.github.jixiaoyong.wanandroid.view.BannerViewHelper
import io.github.jixiaoyong.wanandroid.view.DispatchNestedScrollView
import io.github.jixiaoyong.wanandroid.viewmodel.MainViewModel
import io.github.jixiaoyong.wanandroid.viewmodel.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
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

    private lateinit var dataBinding: FragmentMainIndexBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_index, container, false)
        val view = dataBinding.root

        mainViewModel = ViewModelProviders.of(
            requireActivity(),
            InjectUtils.provideMainViewModelFactory()
        ).get(MainViewModel::class.java)
        searchViewModel = ViewModelProviders.of(
            requireActivity(),
            InjectUtils.provideSearchModelFactory()
        ).get(SearchViewModel::class.java)

        setupFakeStateBar(dataBinding.stateBarView)

        initView(view)
        return view
    }

    private fun initView(view: View) {
        // 在这里初始化recycleView的高度 = 屏幕高度 - bottomNavView
        val rootView = requireActivity().window.decorView.rootView
        rootView.post {
            val listHeight = rootView.height - (requireActivity() as BottomNabControl).getBottomNavViewHeight()
            val listParams = dataBinding.postRecyclerView.layoutParams
            listParams.height = listHeight
            dataBinding.postRecyclerView.layoutParams = listParams
        }

        dataBinding.nestedScrollView.isNeedInterceptActionMove = this::isNeedIntercept
        dataBinding.swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    mainViewModel.loadIndexPostFromZero()
                }
                dataBinding.swipeRefreshLayout.isRefreshing = false
            }
        }

        val postAdapter = MainIndexPagingAdapter(
            mainViewModel::updateIndexPostCollectState,
            isLogin = mainViewModel::isLogin
        )
        dataBinding.postRecyclerView.adapter = postAdapter
        dataBinding.postRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        lifecycleScope.launch {
            mainViewModel.allIndexPost().collectLatest {
                postAdapter.submitData(it)
            }
        }

        mainViewModel.bannerListLiveData.observe(
            viewLifecycleOwner,
            { list ->
                list?.let { dataList ->
                    Logger.d("banner data list $dataList")
                    val fragments = BannerViewHelper.initImageBannerOf(
                        dataList.size
                    ) { imageView, i ->
                        val imgUrl = dataList.getOrNull(i)?.imagePath
                        imageView.setOnClickListener {
                            dataList.getOrNull(i)?.url?.let {
                                NetUtils.loadUrl(requireContext(), it)
                            }
                        }
                        Glide.with(imageView)
                            .load(imgUrl)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.ic_loading)
                            .error(R.drawable.ic_loading)
                            .centerCrop()
                            .into(imageView)
                    }

                    dataBinding.banner.setViewsAndIndicator(
                        childFragmentManager, fragments,
                        dataList.map {
                            it.title
                        }
                    )
                }
            }
        )

        dataBinding.weChatBtn.setOnClickListener {
            goMoreFragment(view, CommonConstants.Action.WECHAT)
        }
        dataBinding.favoriteBtn.setOnClickListener {
            if (mainViewModel.isLogin.value == true) {
                goMoreFragment(view, CommonConstants.Action.FAVORITE)
            } else {
                toast(getString(R.string.tips_plz_login))
            }
        }
        dataBinding.peopleBtn.setOnClickListener {
            goMoreFragment(view, CommonConstants.Action.PEOPLE)
        }

        dataBinding.searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            refreshSearchView()
            if (hasFocus) {
                lifecycleScope.launch {
                    dataBinding.progressBar.visibility = View.VISIBLE
                    val hotKeyWords = withContext(Dispatchers.IO) {
                        searchViewModel.getHotSearchWords()
                    }
                    dataBinding.searchList.adapter = SearchAdapter(hotKeyWords, this@MainIndexFragment::searchKeyWord)
                    dataBinding.progressBar.visibility = View.GONE
                }
            }
        }

        dataBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Logger.d("onQueryTextSubmit:$query")
                dataBinding.searchView.setQuery("", false)
                searchKeyWord(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        refreshSearchView()
    }

    private fun refreshSearchView() {
        dataBinding.searchList.visibility = if (dataBinding.searchView.hasFocus()) View.VISIBLE else View.GONE
        Logger.d(
            "refreshSearchView:${dataBinding.searchView.hasFocus()}\n" +
                "searchViewLayout.visibility:${dataBinding.searchList.visibility}"
        )
    }

    private fun searchKeyWord(query: String?) {
        query?.run {
            ImmUtils.hideImm(requireActivity())
            val bundle = bundleOf(Pair(CommonConstants.KEYS.SEARCH_ARGS, this))
            goMoreFragment(requireView(), CommonConstants.Action.SEARCH, bundle)
            dataBinding.searchList.visibility = View.GONE
        }
    }

    @Synchronized
    private fun goMoreFragment(view: View, action: Int, bundle: Bundle? = null) {
        val args = Bundle()
        args.putInt(CommonConstants.Action.KEY, action)
        bundle?.let {
            args.putAll(it)
        }
        view.findNavController().navigate(R.id.action_mainIndexFragment_to_moreFragment, args)
    }

    // DispatchNestedScrollView是否需要拦截子View的滑动事件
    private fun isNeedIntercept(ev: MotionEvent?, direction: Int): Boolean {
        ev?.let {
            dataBinding?.postRecyclerView?.let {
                if (Utils.isInViewScope(it, ev.rawX, ev.rawY) &&
                    (0 >= (dataBinding!!.dividerAfterHotImg!!.y - dataBinding!!.nestedScrollView!!.scrollY)) &&
                    direction == DispatchNestedScrollView.Companion.Direction.VERTICAL
                ) {
                    return false
                }
            }
            dataBinding?.banner?.let {
                if (Utils.isInViewScope(it, ev.rawX, ev.rawY) &&
                    direction == DispatchNestedScrollView.Companion.Direction.HORIZONTAL
                ) {
                    return false
                }
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_index, menu)
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
    }
}
