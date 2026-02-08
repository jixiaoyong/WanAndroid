package io.github.jixiaoyong.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.adapter.ProjectFragmentAdapter
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import io.github.jixiaoyong.wanandroid.databinding.FragmentMainProjectBinding
import io.github.jixiaoyong.wanandroid.utils.InjectUtils
import io.github.jixiaoyong.wanandroid.viewmodel.ProjectViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-06
 * description: 项目
 */
class MainProjectFragment : BaseFragment() {

    private val viewModel: ProjectViewModel by viewModels { InjectUtils.provideProjectViewModelFactory() }
    private lateinit var dataBinding: FragmentMainProjectBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_project, container, false)
        setupFakeStateBar(dataBinding.stateBarView)

        initView()
        return dataBinding.root
    }

    private fun initView() {
        lifecycleScope.launchWhenCreated {
            val data = withContext(Dispatchers.IO) { viewModel.mainTabs() }
            data?.forEachIndexed { index, dataProjectParam ->
                val tabItem = dataBinding.tabLayout.newTab()
                tabItem.text = dataProjectParam.name
                tabItem.tag = index
                dataBinding.tabLayout.addTab(tabItem)
            }
            dataBinding.viewPager.adapter = data?.let { it1 ->
                ProjectFragmentAdapter(it1, requireActivity())
            }
            dataBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    dataBinding.tabLayout.getTabAt(position)?.select()
                }
            })
        }

        dataBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {}

            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val index = p0?.tag as Int?
                dataBinding.viewPager.currentItem = index ?: 0
            }
        })
    }
}
