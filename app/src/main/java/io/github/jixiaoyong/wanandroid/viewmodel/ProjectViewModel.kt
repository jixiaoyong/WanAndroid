package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.*
import androidx.paging.toLiveData
import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.api.ApiCommondConstants
import io.github.jixiaoyong.wanandroid.api.bean.DataProjectParam
import io.github.jixiaoyong.wanandroid.base.BaseViewModel
import io.github.jixiaoyong.wanandroid.data.IndexPostBoundaryCallback
import io.github.jixiaoyong.wanandroid.data.NetWorkRepository
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
class ProjectViewModel(netWorkRepository: NetWorkRepository) : BaseViewModel() {

    val mainTabs: LiveData<List<DataProjectParam>?> = liveData(coroutineContext) {
        val result = netWorkRepository.getMainProjectList().data
        emit(result)
    }

    val currentMainTabIndex = MutableLiveData(0)
    val currentMainTabItem = MediatorLiveData<DataProjectParam?>()


    init {
        currentMainTabItem.addSource(mainTabs) {
            updateCurrentTabItem(it, currentMainTabIndex.value ?: 0)

        }
        currentMainTabItem.addSource(currentMainTabIndex) {
            updateCurrentTabItem(mainTabs.value, it)
        }

    }

    fun updateCurrentTabItem(mainTabs: List<DataProjectParam>?, index: Int) {
        thread {
            DatabaseUtils.database.baseArticlesDao().deleteAllArticles(ApiCommondConstants.PostType.ProjectPost)
            currentMainTabItem.postValue(mainTabs?.getOrNull(index))
        }
    }

    val allProjectPost = Transformations.switchMap(currentMainTabItem) { tabItem ->
        Logger.d("currentSubTabItem change${tabItem?.name} ${tabItem?.id}")
        tabItem?.let {
            DatabaseUtils.database
                    .baseArticlesDao().queryAllArticles(ApiCommondConstants.PostType.ProjectPost).toLiveData(
                            pageSize = CommonConstants.Paging.PAGE_SIZE,
                            boundaryCallback = IndexPostBoundaryCallback { currentPage ->
                                launch(Dispatchers.IO) {
                                    Logger.e("project list : start load $currentPage")
                                    netWorkRepository.getProjectPostOnPage(currentPage, it.id)
                                }
                            }
                    )
        }
    }
}