package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.*
import androidx.paging.toLiveData
import cf.android666.applibrary.Logger
import com.google.gson.Gson
import io.github.jixiaoyong.wanandroid.api.ApiCommondConstants
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam
import io.github.jixiaoyong.wanandroid.api.bean.DataProjectParam
import io.github.jixiaoyong.wanandroid.base.BaseViewModel
import io.github.jixiaoyong.wanandroid.data.*
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import io.github.jixiaoyong.wanandroid.utils.DatabaseUtils
import io.github.jixiaoyong.wanandroid.utils.NetUtils
import io.github.jixiaoyong.wanandroid.utils.jsonToListOf
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
class ProjectViewModel(private val netWorkRepository: NetWorkRepository) : BaseViewModel() {

    val netState = MutableLiveData<NetUtils.NetworkState>(NetUtils.NetworkState.Normal)

    private val mainTabsPreference: LiveData<Preference?> by lazy {
        DatabaseUtils.database
            .preferenceDao().queryPreferenceByKey(CommonConstants.PreferenceKey.PROJECT_TABS)
    }

    val mainTabs = Transformations.map(mainTabsPreference) {
        formatStringToTabs(it?.value)
    }

    val currentMainTabIndex = MutableLiveData(0)
    private val currentMainTabItem = MediatorLiveData<DataProjectParam?>()

    init {
        currentMainTabItem.addSource(mainTabs) {
            updateCurrentTabItem(it, currentMainTabIndex.value ?: 0)
        }
        currentMainTabItem.addSource(currentMainTabIndex) {
            updateCurrentTabItem(mainTabs.value, it)
        }
    }

    private fun updateCurrentTabItem(mainTabs: List<DataProjectParam>?, index: Int) {
        thread {
            DatabaseUtils.database.baseArticlesDao().deleteAllArticles(ApiCommondConstants.PostType.ProjectPost)
            currentMainTabItem.postValue(mainTabs?.getOrNull(index))
        }
    }

    val allProjectPost = Transformations.switchMap(currentMainTabItem) { tabItem ->
        Logger.d("currentSubTabItem change${tabItem?.name} ${tabItem?.id}")
        tabItem?.let {
            DatabaseUtils.database.baseArticlesDao()
                .queryAllArticles(ApiCommondConstants.PostType.ProjectPost).toLiveData(
                    pageSize = CommonConstants.Paging.PAGE_SIZE,
                    boundaryCallback = PostBoundaryCallback { page ->
                        // project page start from 1
                        val currentPage = page + 1
                        viewModelScope.launch(Dispatchers.IO) {
                            Logger.e("project list : start load $currentPage")
                            netState.postValue(NetUtils.NetworkState.Loading)
                            netWorkRepository.getProjectPostOnPage(currentPage, it.id)
                            netState.postValue(NetUtils.NetworkState.Succeeded)
                            Logger.e("project list : finish load $currentPage")
                        }
                    }
                )
        }
    }

//    fun allProjectPost(): Flow<PagingData<DataIndexPostParam>> {
//        DatabaseUtils.database.baseArticlesDao().queryAllArticles(ApiCommondConstants.PostType.ProjectPost)
//        return Pager(
//                PagingConfig(CommonConstants.Paging.PAGE_SIZE, enablePlaceholders = false),
//                pagingSourceFactory = { PostPagingSource(){page->
//                    
//                } }
//        ).flow
//    }

    fun updateIndexPostCollectState(dataIndexPostParam: DataIndexPostParam) {
        netWorkRepository.updatePostCollectState(dataIndexPostParam)
    }

    private fun formatStringToTabs(string: String?): List<DataProjectParam>? {
        return if (!string.isNullOrBlank()) {
            string.jsonToListOf()
        } else {
            thread {
                try {
                    val result = netWorkRepository.getMainProjectList().execute().body()?.data
                    val jsonString = Gson().toJson(result)
                    val preference = Preference(CommonConstants.PreferenceKey.PROJECT_TABS, jsonString)
                    DatabaseUtils.database.preferenceDao().insert(preference)
                } catch (e: Exception) {
                    Logger.e("获取PROJECT tabs失败", e)
                }
            }
            null
        }
    }

    fun refreshProjectList() {
        thread {
            DatabaseUtils.database.baseArticlesDao().deleteAllArticles(ApiCommondConstants.PostType.ProjectPost)
        }
    }
}
