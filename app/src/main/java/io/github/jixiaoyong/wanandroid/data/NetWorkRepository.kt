package io.github.jixiaoyong.wanandroid.data

import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.api.ApiCommondConstants
import io.github.jixiaoyong.wanandroid.api.bean.*
import io.github.jixiaoyong.wanandroid.utils.DatabaseUtils
import io.github.jixiaoyong.wanandroid.utils.NetUtils

/**
 *  Created by jixiaoyong1995@gmail.com
 *  Data: 2019/11/15.
 *  Description: 网络相关操作类
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
        DatabaseUtils.database.baseArticlesDao().update(dataIndexPostParam)
        Logger.d("update zan state database:${DatabaseUtils.database.baseArticlesDao().queryArticlesByPostId(dataIndexPostParam.id)}")

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

    suspend fun getPeoplePostOnPage(currentPage: Int): RemoteDataBean<DataPageOf<DataIndexPostParam>> {
        val result = NetUtils.wanAndroidApi.getPulicPlacePosts(currentPage)

        val wechatPostList = result.data?.datas?.map {
            it._postType = ApiCommondConstants.PostType.PeoplePost
            it
        } ?: listOf()
        if (currentPage == 0) {
            DatabaseUtils.database.baseArticlesDao().deleteAllArticles(ApiCommondConstants.PostType.PeoplePost)
        }
        DatabaseUtils.database.baseArticlesDao().insert(wechatPostList)
        return result
    }

    suspend fun getFavoritePostOnPage(currentPage: Int): RemoteDataBean<DataPageOf<DataIndexPostParam>> {
        val result = NetUtils.wanAndroidApi.getCollect(currentPage)

        val wechatPostList = result.data?.datas?.map {
            it._postType = ApiCommondConstants.PostType.FavoritePost
            it
        } ?: listOf()
        if (currentPage == 0) {
            DatabaseUtils.database.baseArticlesDao().deleteAllArticles(ApiCommondConstants.PostType.FavoritePost)
        }
        DatabaseUtils.database.baseArticlesDao().insert(wechatPostList)
        return result
    }

    /**
     * 获取微信公众号列表
     */
    suspend fun getWechatList(): RemoteDataBean<List<DataSystemParam<Any>>> {
        return NetUtils.wanAndroidApi.getWechatList()
    }

    suspend fun getWechatPostOnPage(page: Int, cid: Int): RemoteDataBean<DataPageOf<DataIndexPostParam>> {
        val result = NetUtils.wanAndroidApi.getWechatPost(cid, page)

        val wechatPostList = result.data?.datas?.map {
            it._postType = ApiCommondConstants.PostType.WechatPost
            it
        } ?: listOf()
        if (page == 0) {
            DatabaseUtils.database.baseArticlesDao().deleteAllArticles(ApiCommondConstants.PostType.WechatPost)
        }
        DatabaseUtils.database.baseArticlesDao().insert(wechatPostList)
        return result
    }

    /**
     * 获取Banner列表
     */
    fun getBannerListSync(): List<DataBannerParam>? {
        val respone = try {
            NetUtils.wanAndroidApi.getBanner().execute()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return respone?.body()?.data
    }

}