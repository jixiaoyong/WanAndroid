package io.github.jixiaoyong.wanandroid.data

import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.api.ApiCommondConstants
import io.github.jixiaoyong.wanandroid.api.bean.*
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
            DatabaseUtils.database.baseArticlesDao().deleteAllArticles(ApiCommondConstants.PostType.IndexPost)
        }
        DatabaseUtils.database.baseArticlesDao().insert(result.data?.datas ?: listOf())
        return result
    }

    fun updatePostCollectState(dataIndexPostParam: DataIndexPostParam) {
        Logger.d("dataIndexPostParam:$dataIndexPostParam")
        DatabaseUtils.database.baseArticlesDao().update(dataIndexPostParam)
        //todo refresh collect state if network result filed
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

    /**
     * 获取项目分类列表
     */
    suspend fun getMainProjectList(): RemoteDataBean<List<DataProjectParam>> {
        return NetUtils.wanAndroidApi.getProjectList()
    }

    /**
     * 获取项目分类列表
     */
    suspend fun getMainSystemList(): RemoteDataBean<List<DataSystemParam<DataSystemParam<Any>>>> {
        return NetUtils.wanAndroidApi.getTree()
    }

    /**
     * 获取项目分类列表
     */
    fun cleanMainSystemPostList() {
        DatabaseUtils.database.baseArticlesDao().deleteAllArticles(ApiCommondConstants.PostType.SystemPost)
    }

    suspend fun getSystemPostOnPage(page: Int, cid: Int): RemoteDataBean<DataPageOf<DataIndexPostParam>> {
        val result = NetUtils.wanAndroidApi.getSystemPost(page, cid)
        Logger.d("sys list:($page,$cid) : getSystemPostOnPage ${result.data?.datas}")

        val sysPostList = result.data?.datas?.map {
            it._postType = ApiCommondConstants.PostType.SystemPost
            it
        } ?: listOf()
        if (page == 0) {
            DatabaseUtils.database.baseArticlesDao().deleteAllArticles(ApiCommondConstants.PostType.SystemPost)
        }
        DatabaseUtils.database.baseArticlesDao().insert(sysPostList)
        return result
    }

    suspend fun getProjectPostOnPage(page: Int, cid: Int): RemoteDataBean<DataPageOf<DataIndexPostParam>> {
        val result = NetUtils.wanAndroidApi.getProjectItems(page, cid)
        Logger.d("sys list:($page,$cid) : getProjectPostOnPage ${result.data}")

        val sysPostList = result.data?.datas?.map {
            it._postType = ApiCommondConstants.PostType.ProjectPost
            it
        } ?: listOf()
        if (page == 0) {
            DatabaseUtils.database.baseArticlesDao().deleteAllArticles(ApiCommondConstants.PostType.ProjectPost)
        }
        DatabaseUtils.database.baseArticlesDao().insert(sysPostList)
        return result
    }
}