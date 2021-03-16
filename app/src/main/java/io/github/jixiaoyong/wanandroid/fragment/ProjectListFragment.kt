package io.github.jixiaoyong.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import cf.android666.applibrary.view.Toast
import com.google.android.material.snackbar.Snackbar
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.activity.LoginRegisterActivity
import io.github.jixiaoyong.wanandroid.adapter.MainProjectPagingAdapter
import io.github.jixiaoyong.wanandroid.databinding.FragmentListBinding
import io.github.jixiaoyong.wanandroid.utils.InjectUtils
import io.github.jixiaoyong.wanandroid.viewmodel.MainViewModel
import io.github.jixiaoyong.wanandroid.viewmodel.ProjectViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2021-03-16
 * description:
 */
class ProjectListFragment : Fragment() {

    private lateinit var viewBinding: FragmentListBinding
    private val viewModel: ProjectViewModel by viewModels { InjectUtils.provideProjectViewModelFactory() }
    private val mainViewModel: MainViewModel by viewModels({ requireActivity() }) { InjectUtils.provideMainViewModelFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentListBinding.inflate(inflater)

        viewBinding.subTabLayout.isVisible = false
        viewBinding.subArrowDownImgBtn.isVisible = false

        initView()

        return viewBinding.root
    }

    private fun initView() {
        val adapter = MainProjectPagingAdapter(
            viewModel::updateIndexPostCollectState, isLogin = mainViewModel::isLogin
        )
        viewBinding.postRecyclerView.adapter = adapter
        viewBinding.postRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        val childId = arguments?.getInt(KEY_CHILD_ID, 0) ?: return

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
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    viewBinding.postRecyclerView.scrollToPosition(0)
                }
        }
        lifecycleScope.launch {
            viewModel.allProjectPost(childId).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun showNotLoginSnackBar() {
        Snackbar.make(viewBinding.swipeRefreshLayout, R.string.tips_plz_login, Snackbar.LENGTH_SHORT)
            .setAction("登录") {
                LoginRegisterActivity.start(requireContext())
            }.show()
    }

    companion object {
        const val KEY_CHILD_ID = "KEY_CHILD_ID"

        fun getInstance(children: Int): ProjectListFragment {
            val fragment = ProjectListFragment()
            fragment.arguments = Bundle().also {
                it.putInt(KEY_CHILD_ID, children)
            }
            return fragment
        }
    }
}
