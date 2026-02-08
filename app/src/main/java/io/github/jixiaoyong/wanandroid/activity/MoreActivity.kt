package io.github.jixiaoyong.wanandroid.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import cf.android666.applibrary.Logger
import com.google.android.material.tabs.TabLayout
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.adapter.MainIndexPagingAdapterOld
import io.github.jixiaoyong.wanandroid.base.BaseActivity
import io.github.jixiaoyong.wanandroid.databinding.FragmentMoreBinding
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import io.github.jixiaoyong.wanandroid.utils.InjectUtils
import io.github.jixiaoyong.wanandroid.viewmodel.MainViewModel
import io.github.jixiaoyong.wanandroid.viewmodel.MoreViewModel

/**
 * description ： 点击首页「微信，收藏，广场」进入的页面
 * @author : jixiaoyong
 * @email : jixiaoyong1995@gmail.com
 * @date : 2021-3-11 15:07:10
 */
class MoreActivity : BaseActivity() {

    private lateinit var viewBinding: FragmentMoreBinding
    private lateinit var viewModel: MoreViewModel

    private val mainViewModel: MainViewModel by viewModels { InjectUtils.provideMainViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = FragmentMoreBinding.inflate(LayoutInflater.from(this))
        setContentView(viewBinding.root)

        val action = intent?.getIntExtra(CommonConstants.Action.KEY, CommonConstants.Action.WECHAT)
            ?: CommonConstants.Action.WECHAT
        val searchArgs = intent?.getStringExtra(CommonConstants.KEYS.SEARCH_ARGS)
        Logger.d("action:$action")
        viewModel = ViewModelProviders.of(
            this,
            InjectUtils.provideMoreViewModelFactory(action, searchArgs)
        ).get(MoreViewModel::class.java)

        setupFakeStateBar(viewBinding.stateBarView)

        initView(action, searchArgs)
    }

    private fun initView(action: Int, searchArgs: String?) {

        val title = when (action) {
            CommonConstants.Action.WECHAT -> getString(R.string.wechat)
            CommonConstants.Action.FAVORITE -> getString(R.string.favortie)
            CommonConstants.Action.PEOPLE -> getString(R.string.square)
            CommonConstants.Action.SEARCH -> getString(R.string.search) + " " + (searchArgs ?: "")
            else -> null
        }

        viewBinding.toolbar.title = title
        viewBinding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val adapter = MainIndexPagingAdapterOld({}, isLogin = mainViewModel::checkUserLoginState)
        viewBinding.postRecyclerView.adapter = adapter
        viewBinding.postRecyclerView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        viewModel.allProjectPost.observe(this, Observer(adapter::submitList))

        if (CommonConstants.Action.WECHAT == action) {
            initTabView()
        }
    }

    private fun initTabView() {
        viewBinding.tabLayoutGroup.visibility = View.VISIBLE
        viewModel.mainTabs?.observe(
            this,
            {
                it?.forEachIndexed { index, dataProjectParam ->
                    val tabItem = viewBinding.tabLayout.newTab()
                    tabItem.text = dataProjectParam.name
                    tabItem.tag = index
                    viewBinding.tabLayout.addTab(tabItem)
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
                viewModel.currentMainTabIndex.value = index ?: 0
            }
        })
    }

    companion object {

        fun start(context: Context, action: Int, args: Bundle? = null) {
            val intent = Intent(context, MoreActivity::class.java)
            intent.putExtra(CommonConstants.Action.KEY, action)
            intent.putExtra(CommonConstants.KEYS.SEARCH_ARGS, args)
            context.startActivity(intent)
        }
    }
}
