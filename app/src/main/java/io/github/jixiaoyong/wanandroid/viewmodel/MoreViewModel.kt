package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.liveData
import androidx.paging.toLiveData
import io.github.jixiaoyong.wanandroid.api.ApiCommondConstants
import io.github.jixiaoyong.wanandroid.api.bean.DataSystemParam
import io.github.jixiaoyong.wanandroid.base.BaseViewModel
import io.github.jixiaoyong.wanandroid.data.NetWorkRepository
import io.github.jixiaoyong.wanandroid.data.PostBoundaryCallback
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import io.github.jixiaoyong.wanandroid.utils.DatabaseUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-16
 * description: 项目
 */
class MoreViewModel(netWorkRepository: NetWorkRepository, action: Int) : BaseViewModel() {

    val mainTabs: LiveData<List<DataSystemParam<Any>>?>? = if (action == CommonConstants.Action.WECHAT) {
        liveData(coroutineContext) {
            val result = netWorkRepository.getWechatList().data
            emit(result)
        }
    } else {
        null
    }

    val currentMainTabIndex = MutableLiveData(0)
    val currentMainTabItem = MediatorLiveData<DataSystemParam<Any>?>()


    init {
        mainTabs?.let {
            currentMainTabItem.addSource(it) {
                updateCurrentTabItem(it, currentMainTabIndex.value ?: 0)

            }
            currentMainTabItem.addSource(currentMainTabIndex) {
                updateCurrentTabItem(mainTabs.value, it)
            }
        }
    }

    fun updateCurrentTabItem(mainTabs: List<DataSystemParam<Any>>?, index: Int) {
        thread {
            DatabaseUtils.database.baseArticlesDao().deleteAllArticles(ApiCommondConstants.PostType.WechatPost)
            currentMainTabItem.postValue(mainTabs?.getOrNull(index))
        }
    }

    val allProjectPost =
            when (action) {
                CommonConstants.Action.PEOPLE -> {
                    DatabaseUtils.database
                            .baseArticlesDao().queryAllArticles(ApiCommondConstants.PostType.PeoplePost).toLiveData(
                                    pageSize = CommonConstants.Paging.PAGE_SIZE,
                                    boundaryCallback = PostBoundaryCallback { currentPage ->
                                        launch(Dispatchers.IO) {
                                            netWorkRepository.getPeoplePostOnPage(currentPage)
                                        }
                                    }
                            )
                }
                CommonConstants.Action.FAVORITE -> {
                    DatabaseUtils.database
                            .baseArticlesDao().queryAllArticles(ApiCommondConstants.PostType.FavoritePost).toLiveData(
                                    pageSize = CommonConstants.Paging.PAGE_SIZE,
                                    boundaryCallback = PostBoundaryCallback { currentPage ->
                                        launch(Dispatchers.IO) {
                                            netWorkRepository.getFavoritePostOnPage(currentPage)
                                        }
                                    }
                            )
                }
                else -> {
                    switchMap(currentMainTabItem) { tabItem ->
                        tabItem?.let {
                            DatabaseUtils.database.baseArticlesDao()
                                    .queryAllArticles(ApiCommondConstants.PostType.WechatPost).toLiveData(
                                            pageSize = CommonConstants.Paging.PAGE_SIZE,
                                            boundaryCallback = PostBoundaryCallback { currentPage ->
                                                launch(Dispatchers.IO) {
                                                    netWorkRepository.getWechatPostOnPage(currentPage, it.id)
                                                }
                                            }
                                    )
                        }

                    }

                }
            }
}