package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import io.github.jixiaoyong.wanandroid.api.bean.DataProjectParam
import io.github.jixiaoyong.wanandroid.base.BaseViewModel
import io.github.jixiaoyong.wanandroid.data.NetWorkRepository

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-16
 * description: 项目
 */
class ProjectViewModel(netWorkRepository: NetWorkRepository) : BaseViewModel() {

    val mainTabs: List<DataProjectParam>? by lazy {
        netWorkRepository.getMainProjectList().data
    }

    val currentMainTabIndex = MutableLiveData(0)
    val currentSubTabIndex = MutableLiveData(0)

    val currentMainTabItem = map(currentMainTabIndex) {
        mainTabs?.getOrNull(it)
    }
    val currentSubTabItems = map(currentMainTabItem) {
        it?.children
    }

    init {


    }
}