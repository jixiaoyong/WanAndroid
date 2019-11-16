package io.github.jixiaoyong.wanandroid.data

import io.github.jixiaoyong.wanandroid.api.bean.AppUpgradeBean
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam
import io.github.jixiaoyong.wanandroid.api.bean.DataPageOf
import io.github.jixiaoyong.wanandroid.api.bean.RemoteDataBean
import io.github.jixiaoyong.wanandroid.utils.DatabaseUtils
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

    suspend fun getIndexPostOnPage(page: Int): RemoteDataBean<DataPageOf<DataIndexPostParam>> {
        val result = NetUtils.wanAndroidApi.getArticles(page)
        if (page == 0) {
            DatabaseUtils.database.baseArticlesDao().deleteAllArticles()
        }
        DatabaseUtils.database.baseArticlesDao().insert(result.data?.datas ?: listOf())
        return result
    }

    fun updateIndexPostCollectState(dataIndexPostParam: DataIndexPostParam) {
        DatabaseUtils.database.baseArticlesDao().update(dataIndexPostParam)

        val call = if (dataIndexPostParam.collect) {
            NetUtils.wanAndroidApi.collectPostById(dataIndexPostParam.id)
        } else {
            NetUtils.wanAndroidApi.uncollectById(dataIndexPostParam.id)
        }
        try {
            call.execute()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}