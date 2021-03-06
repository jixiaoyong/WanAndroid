package io.github.jixiaoyong.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import io.github.jixiaoyong.wanandroid.adapter.ListFragmentAdapter
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import io.github.jixiaoyong.wanandroid.databinding.FragmentMainSystemBinding
import io.github.jixiaoyong.wanandroid.utils.InjectUtils
import io.github.jixiaoyong.wanandroid.viewmodel.SystemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-06
 * description: 体系
 */
class MainSystemFragment : BaseFragment() {

    private lateinit var viewBinding: FragmentMainSystemBinding

    private val viewModel: SystemViewModel by viewModels { InjectUtils.provideSystemViewModelFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentMainSystemBinding.inflate(inflater)

        setupFakeStateBar(viewBinding.stateBarView)

        initView()
        return viewBinding.root
    }

    private fun initView() {

        lifecycleScope.launchWhenCreated {
            val data = withContext(Dispatchers.IO) {
                viewModel.mainTabs()
            }
            data?.forEachIndexed { index, dataSystemParam ->
                val tabItem = viewBinding.tabLayout.newTab()
                tabItem.text = dataSystemParam.name
                tabItem.tag = index
                viewBinding.tabLayout.addTab(tabItem)
            }

            viewBinding.viewPager.adapter = data?.let { it1 ->
                ListFragmentAdapter(it1, requireActivity())
            }
            viewBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewBinding.tabLayout.getTabAt(position)?.select()
                }
            })
        }

        viewBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {}

            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val index = p0?.tag as Int?
                viewBinding.viewPager.setCurrentItem(index ?: 0, false)
            }
        })
    }
}
