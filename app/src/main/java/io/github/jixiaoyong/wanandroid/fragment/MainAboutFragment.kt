package io.github.jixiaoyong.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.BuildConfig
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.base.BaseFragment
import io.github.jixiaoyong.wanandroid.base.toast
import io.github.jixiaoyong.wanandroid.databinding.FragmentMainAboutBinding
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import io.github.jixiaoyong.wanandroid.utils.InjectUtils
import io.github.jixiaoyong.wanandroid.utils.NetUtils
import io.github.jixiaoyong.wanandroid.viewmodel.AboutViewModel
import io.github.jixiaoyong.wanandroid.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-06
 * description: 关于
 */
class MainAboutFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentMainAboutBinding
    private val mainViewModel: MainViewModel by viewModels({ requireActivity() }) { InjectUtils.provideMainViewModelFactory() }
    private val aboutViewModel: AboutViewModel by viewModels { InjectUtils.provideAboutViewModelFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_about, container, false)
        val view = dataBinding.root

        dataBinding.viewModel = mainViewModel
        dataBinding.lifecycleOwner = this

        initView()
        return view
    }

    private fun initView() {
        dataBinding.versionMore.text = "v${BuildConfig.VERSION_NAME}"
        dataBinding.versionTv.setOnClickListener {
            toast("${getString(R.string.app_version)}: ${dataBinding.versionMore.text}")
        }

        dataBinding.webTv.setOnClickListener {
            goContentActivity(CommonConstants.WebSites.APP_URL)
        }

        dataBinding.authorTv.setOnClickListener {
            goContentActivity(CommonConstants.WebSites.AUTHOR_URL)
        }

        dataBinding.shareTv.setOnClickListener {
            // todo show share dialog
        }

        dataBinding.upgradeTv.setOnClickListener {
            lifecycleScope.launch {
                // todo show start check upgrade tips
                toast(getString(R.string.tips_start_check_upgrade))
                val upgradeInfo = withContext(Dispatchers.IO) {
                    try {
                        aboutViewModel.checkUpgrade()
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            toast(R.string.tips_network_error)
                        }
                        Logger.e("check upgrade filed")
                        e.printStackTrace()
                        null
                    }
                }
                upgradeInfo?.let {
                    if (it.versionCode > BuildConfig.VERSION_CODE) {
                        // todo show upgrade dialog
                        Logger.d("new version comming!\n ${it.summary}")
                    } else {
                        toast(getString(R.string.tips_up_to_date))
                    }
                }
            }
        }
    }

    private fun goContentActivity(url: String) {
        NetUtils.loadUrl(requireContext(), url)
    }
}
