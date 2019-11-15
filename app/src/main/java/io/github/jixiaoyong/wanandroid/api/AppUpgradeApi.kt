package io.github.jixiaoyong.wanandroid.api

import io.github.jixiaoyong.wanandroid.api.bean.AppUpgradeBean
import retrofit2.http.GET

/**
 *  Created by jixiaoyong1995@gmail.com
 *  Data: 2019/11/15.
 *  Description: 检测更新API接口
 */
interface AppUpgradeApi {

    @GET("download/data/wanandroid/updateInfo.json")
    suspend fun checkUpgradeInfo(): AppUpgradeBean
}