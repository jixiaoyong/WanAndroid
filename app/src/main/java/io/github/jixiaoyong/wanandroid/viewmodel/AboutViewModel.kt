package io.github.jixiaoyong.wanandroid.viewmodel

import io.github.jixiaoyong.wanandroid.api.bean.AppUpgradeBean
import io.github.jixiaoyong.wanandroid.base.BaseViewModel
import io.github.jixiaoyong.wanandroid.data.NetWorkRepository

/**
 *  Created by jixiaoyong1995@gmail.com
 *  Data: 2019/11/15.
 *  Description:
 */
class AboutViewModel(private val netWorkRepository: NetWorkRepository) : BaseViewModel() {

    suspend fun checkUpgrade(): AppUpgradeBean {
        return netWorkRepository.checkUpdate()
    }
}
