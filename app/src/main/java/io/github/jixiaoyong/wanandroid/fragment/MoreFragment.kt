package io.github.jixiaoyong.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import cf.android666.applibrary.Logger
import com.google.android.material.tabs.TabLayout
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.adapter.MainIndexPagingAdapter
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import io.github.jixiaoyong.wanandroid.databinding.FragmentMoreBinding
import io.github.jixiaoyong.wanandroid.utils.BottomNabControl
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import io.github.jixiaoyong.wanandroid.utils.InjectUtils
import io.github.jixiaoyong.wanandroid.viewmodel.MainViewModel
import io.github.jixiaoyong.wanandroid.viewmodel.MoreViewModel

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-17
 * description: 点击首页「微信，收藏，广场」进入的页面
 */
class MoreFragment : BaseFragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: MoreViewModel
    private lateinit var dataBinding: FragmentMoreBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false)
        val view = dataBinding.root

        val action = arguments?.getInt(CommonConstants.Action.KEY) ?: CommonConstants.Action.WECHAT
        val searchArgs = arguments?.getString(CommonConstants.KEYS.SEARCH_ARGS)
        Logger.d("action:$action")
        viewModel = ViewModelProviders.of(
            this,
            InjectUtils.provideMoreViewModelFactory(action, searchArgs)
        ).get(MoreViewModel::class.java)
        mainViewModel = ViewModelProviders.of(
            requireActivity(),
            InjectUtils.provideMainViewModelFactory()
        ).get(MainViewModel::class.java)
        setupFakeStateBar(dataBinding.stateBarView)

        initView(view, action, searchArgs)
        return view
    }

    private fun initView(view: View, action: Int, searchArgs: String?) {

        val title = when (action) {
            CommonConstants.Action.WECHAT -> getString(R.string.wechat)
            CommonConstants.Action.FAVORITE -> getString(R.string.favortie)
            CommonConstants.Action.PEOPLE -> getString(R.string.square)
            CommonConstants.Action.SEARCH -> getString(R.string.search) + " " + (searchArgs ?: "")
            else -> null
        }

        dataBinding.toolbar.title = title
        dataBinding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }
        )

        val adapter = MainIndexPagingAdapter({}, isLogin = mainViewModel::isLogin)
        dataBinding.postRecyclerView.adapter = adapter
        dataBinding.postRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        viewModel.allProjectPost.observe(viewLifecycleOwner, Observer(adapter::submitList))

        if (CommonConstants.Action.WECHAT == action) {
            initTabView(view)
        }
    }

    override fun onResume() {
        super.onResume()
        changBottomNabViewVisible(false)
    }

    override fun onPause() {
        super.onPause()
        changBottomNabViewVisible(true)
    }

    private fun changBottomNabViewVisible(isVisible: Boolean) {
        val activity = requireActivity()
        if (activity is BottomNabControl) {
            activity.changBottomNavViewVisibility(isVisible)
        }
    }

    private fun initTabView(view: View) {
        dataBinding.tabLayoutGroup.visibility = View.VISIBLE
        viewModel.mainTabs?.observe(
            viewLifecycleOwner,
            Observer {
                it?.forEachIndexed { index, dataProjectParam ->
                    val tabItem = dataBinding.tabLayout.newTab()
                    tabItem.text = dataProjectParam.name
                    tabItem.tag = index
                    dataBinding.tabLayout.addTab(tabItem)
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
                viewModel.currentMainTabIndex.value = index ?: 0
            }
        })
    }

    fun onBackPressed() {
        val naviagtion = Navigation.findNavController(requireActivity(), R.id.fragmentNav)
        naviagtion.navigateUp()
    }
}
