package io.github.jixiaoyong.wanandroid.data

import io.github.jixiaoyong.wanandroid.api.bean.AppUpgradeBean
import io.github.jixiaoyong.wanandroid.utils.NetUtils

/**
 *  Created by jixiaoyong1995@gmail.com
 *  Data: 2019/11/15.
 *  Description:
 */
class NetWorkRepository {

    suspend fun checkUpdate(): AppUpgradeBean {
        return NetUtils.appUpgradeApi.checkUpgradeInfo()
    }
}