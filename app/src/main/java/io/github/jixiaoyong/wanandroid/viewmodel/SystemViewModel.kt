package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.liveData
import androidx.paging.toLiveData
import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.api.ApiCommondConstants
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam
import io.github.jixiaoyong.wanandroid.api.bean.DataSystemParam
import io.github.jixiaoyong.wanandroid.base.BaseViewModel
import io.github.jixiaoyong.wanandroid.data.IndexPostBoundaryCallback
import io.github.jixiaoyong.wanandroid.data.NetWorkRepository
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import io.github.jixiaoyong.wanandroid.utils.DatabaseUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-16
 * description: todo
 */
class SystemViewModel(val netWorkRepository: NetWorkRepository) : BaseViewModel() {

    val mainTabs: LiveData<List<DataSystemParam<DataSystemParam<Any>>>?> = liveData(coroutineContext) {
        val result = netWorkRepository.getMainSystemList().data
        emit(result)
    }

    val currentMainTabIndex = MutableLiveData(0)
    val currentSubTabIndex = MutableLiveData(0)

    val currentMainTabItem = MediatorLiveData<DataSystemParam<DataSystemParam<Any>>?>()
    val currentSubTabItem = MediatorLiveData<DataSystemParam<Any>?>()
    val currentSubTabItems = MediatorLiveData<List<DataSystemParam<Any>?>?>()


    init {
        currentMainTabItem.addSource(mainTabs) {
            currentMainTabItem.value = it?.getOrNull(currentMainTabIndex.value ?: 0)
        }
        currentMainTabItem.addSource(currentMainTabIndex) {
            currentMainTabItem.value = mainTabs.value?.getOrNull(it)
        }

        currentSubTabItems.addSource(mainTabs) {
            currentSubTabItems.value = it?.getOrNull(currentMainTabIndex.value ?: 0)?.children
        }
        currentSubTabItems.addSource(currentMainTabIndex) {
            currentSubTabItems.value = mainTabs.value?.getOrNull(it)?.children
        }

        currentSubTabItem.addSource(currentSubTabItems) {
            updateCurrentSubTabItem(it, currentSubTabIndex.value ?: 0)
        }
        currentSubTabItem.addSource(currentSubTabIndex) {
            updateCurrentSubTabItem(currentSubTabItems.value, it)
        }

    }

    private fun updateCurrentSubTabItem(it: List<DataSystemParam<Any>?>?, currentSubTabIndex: Int) {
        launch {
            withContext(Dispatchers.IO) {
                netWorkRepository.cleanMainSystemPostList()
            }
            currentSubTabItem.postValue(it?.getOrNull(currentSubTabIndex))
        }
    }

    val allSystemPost = switchMap(currentSubTabItem) { subTabItem ->
        Logger.d("currentSubTabItem change${subTabItem?.name} ${subTabItem?.id}")
        subTabItem?.let {
            DatabaseUtils.database
                    .baseArticlesDao().queryAllArticles(ApiCommondConstants.PostType.SystemPost).toLiveData(
                            pageSize = CommonConstants.Paging.PAGE_SIZE,
                            boundaryCallback = IndexPostBoundaryCallback { currentPage ->
                                launch(Dispatchers.IO) {
                                    Logger.e("sys list : start load $currentPage")
                                    netWorkRepository.getSystemPostOnPage(currentPage, it.id)
                                }
                            }
                    )
        }
    }

    fun updateISystemPostCollectState(systemPostParam: DataIndexPostParam) {
        netWorkRepository.updatePostCollectState(systemPostParam)
    }
}