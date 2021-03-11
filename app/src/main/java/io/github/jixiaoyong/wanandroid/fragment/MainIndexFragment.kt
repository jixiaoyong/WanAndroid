package io.github.jixiaoyong.wanandroid.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import cf.android666.applibrary.Logger
import cf.android666.applibrary.view.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.activity.LoginRegisterActivity
import io.github.jixiaoyong.wanandroid.activity.MoreActivity
import io.github.jixiaoyong.wanandroid.adapter.LoadMoreAdapter
import io.github.jixiaoyong.wanandroid.adapter.MainIndexPagingAdapter
import io.github.jixiaoyong.wanandroid.adapter.SearchAdapter
import io.github.jixiaoyong.wanandroid.base.BaseFragment
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
    private val mainViewModel: MainViewModel by viewModels({ requireActivity() }) { InjectUtils.provideMainViewModelFactory() }
    private val searchViewModel: SearchViewModel by viewModels { InjectUtils.provideSearchModelFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dataBinding = FragmentMainIndexBinding.inflate(layoutInflater)
        setupFakeStateBar(dataBinding.stateBarView)

        initView()
        return dataBinding.root
    }

    private fun initView() {
        // 在这里初始化recycleView的高度 = 屏幕高度 - bottomNavView
        val rootView = requireActivity().window.decorView.rootView
        rootView.post {
            val listHeight = rootView.height - (requireActivity() as BottomNabControl).getBottomNavViewHeight()
            val listParams = dataBinding.postRecyclerView.layoutParams
            listParams.height = listHeight
            dataBinding.postRecyclerView.layoutParams = listParams
        }

        val postAdapter = initAdapter()

        dataBinding.nestedScrollView.isNeedInterceptActionMove = this::isNeedIntercept
        dataBinding.swipeRefreshLayout.setOnRefreshListener {
            postAdapter.refresh()
            mainViewModel.getBanner()
        }
        dataBinding.retryButton.setOnClickListener { postAdapter.refresh() }
        // 注意withLoadStateHeaderAndFooter()会产生一个新的[ConcatAdapter],所以需要按照下面方式设置；
        // 或者需要保存其返回值，然后设置给postRecyclerView
        dataBinding.postRecyclerView.adapter = postAdapter.withLoadStateFooter(
            LoadMoreAdapter { postAdapter.retry() }
        )
        dataBinding.postRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        lifecycleScope.launch { mainViewModel.allIndexPost.collectLatest { postAdapter.submitData(it) } }
        lifecycleScope.launch { mainViewModel.getBanner() }

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
            goMoreFragment(CommonConstants.Action.WECHAT)
        }
        dataBinding.favoriteBtn.setOnClickListener {
            if (mainViewModel.isLogin.value == true) {
                goMoreFragment(CommonConstants.Action.FAVORITE)
            } else {
                showNotLoginSnackBar()
            }
        }
        dataBinding.peopleBtn.setOnClickListener {
            goMoreFragment(CommonConstants.Action.PEOPLE)
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

    private fun initAdapter(): MainIndexPagingAdapter {
        val postAdapter = MainIndexPagingAdapter(
            mainViewModel::updateIndexPostCollectState,
            isLogin = mainViewModel::isLogin,
            notLoginFunc = this::showNotLoginSnackBar
        )
        postAdapter.addLoadStateListener { loadState ->
            dataBinding.postRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            dataBinding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
            dataBinding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            dataBinding.swipeRefreshLayout.isRefreshing = loadState.source.refresh is LoadState.Loading

            val errorState = loadState.refresh as? LoadState.Error
                ?: loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.show("\uD83D\uDE28 Wooops ${it.error}")
            }
        }
        return postAdapter
    }

    private fun showNotLoginSnackBar() {
        Snackbar.make(dataBinding.swipeRefreshLayout, R.string.tips_plz_login, Snackbar.LENGTH_SHORT)
            .setAction("登录") {
                LoginRegisterActivity.start(requireContext())
            }.show()
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
            goMoreFragment(CommonConstants.Action.SEARCH, bundle)
            dataBinding.searchList.visibility = View.GONE
        }
    }

    @Synchronized
    private fun goMoreFragment(action: Int, bundle: Bundle? = null) {
        MoreActivity.start(requireContext(), action, bundle)
    }

    // DispatchNestedScrollView是否需要拦截子View的滑动事件
    private fun isNeedIntercept(ev: MotionEvent?, direction: Int): Boolean {
        ev?.let {
            dataBinding.postRecyclerView.let {
                if (Utils.isInViewScope(it, ev.rawX, ev.rawY) &&
                    (0 >= (dataBinding.dividerAfterHotImg.y - dataBinding.nestedScrollView.scrollY)) &&
                    direction == DispatchNestedScrollView.Companion.Direction.VERTICAL
                ) {
                    return false
                }
            }
            dataBinding.banner.let {
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
