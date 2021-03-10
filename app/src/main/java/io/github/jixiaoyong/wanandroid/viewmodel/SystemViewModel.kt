package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.toLiveData
import cf.android666.applibrary.Logger
import com.google.gson.Gson
import io.github.jixiaoyong.wanandroid.api.ApiCommondConstants
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam
import io.github.jixiaoyong.wanandroid.api.bean.DataSystemParam
import io.github.jixiaoyong.wanandroid.base.BaseViewModel
import io.github.jixiaoyong.wanandroid.data.NetWorkRepository
import io.github.jixiaoyong.wanandroid.data.PostBoundaryCallback
import io.github.jixiaoyong.wanandroid.data.Preference
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import io.github.jixiaoyong.wanandroid.utils.DatabaseUtils
import io.github.jixiaoyong.wanandroid.utils.NetUtils
import io.github.jixiaoyong.wanandroid.utils.jsonToListOfDataSys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-16
 * description: 体系页面
 */
class SystemViewModel(val netWorkRepository: NetWorkRepository) : BaseViewModel() {

    val netState = MutableLiveData<NetUtils.NetworkState>(NetUtils.NetworkState.Normal)

    private val mainTabsPreference: LiveData<Preference?> by lazy {
        netState.postValue(NetUtils.NetworkState.Loading)
        DatabaseUtils.database
            .preferenceDao().queryPreferenceByKey(CommonConstants.PreferenceKey.SYSTEM_TABS)
    }

    val mainTabs = map(mainTabsPreference) {
        formatStringToTabs(it?.value)
    }

    val currentMainTabIndex = MutableLiveData(0)
    val currentSubTabIndex = MutableLiveData(0)

    private val currentMainTabItem = MediatorLiveData<DataSystemParam<DataSystemParam<Any>>?>()
    private val currentSubTabItem = MediatorLiveData<DataSystemParam<Any>?>()
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
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                netWorkRepository.cleanMainSystemPostList()
            }
            currentSubTabItem.postValue(it?.getOrNull(currentSubTabIndex))
        }
    }

    val d = GlobalScope

    val allSystemPost = switchMap(currentSubTabItem) { subTabItem ->
        Logger.d("currentSubTabItem change${subTabItem?.name} ${subTabItem?.id}")
        subTabItem?.let {
            DatabaseUtils.database
                .baseArticlesDao().queryAllArticles(ApiCommondConstants.PostType.SystemPost).toLiveData(
                    pageSize = CommonConstants.Paging.PAGE_SIZE,
                    boundaryCallback = PostBoundaryCallback { currentPage ->
                        viewModelScope.launch(Dispatchers.IO) {
                            Logger.e("sys list : start load $currentPage")
                            netState.postValue(NetUtils.NetworkState.Loading)
                            netWorkRepository.getSystemPostOnPage(currentPage, it.id)
                            netState.postValue(NetUtils.NetworkState.Succeeded)
                            Logger.e("sys list : finish load $currentPage")
                        }
                    }
                )
        }
    }

    fun updateISystemPostCollectState(systemPostParam: DataIndexPostParam) {
        netWorkRepository.updatePostCollectState(systemPostParam)
    }

    fun refreshSubTabsData() {
        thread {
            DatabaseUtils.database.baseArticlesDao().deleteAllArticles(ApiCommondConstants.PostType.SystemPost)
        }
    }

    private fun formatStringToTabs(string: String?): List<DataSystemParam<DataSystemParam<Any>>>? {
        return if (!string.isNullOrBlank()) {
            val result = string.jsonToListOfDataSys()
            netState.postValue(NetUtils.NetworkState.Succeeded)
            result
        } else {
            thread {
                try {
                    val result = netWorkRepository.getMainSystemList().execute().body()?.data
                    val jsonString = Gson().toJson(result)
                    val preference = Preference(CommonConstants.PreferenceKey.SYSTEM_TABS, jsonString)
                    DatabaseUtils.database.preferenceDao().insert(preference)
                    netState.postValue(NetUtils.NetworkState.Succeeded)
                } catch (e: Exception) {
                    Logger.e("获取SYS tabs失败", e)
                }
            }
            null
        }
    }
}
