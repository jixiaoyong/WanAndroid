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
import io.github.jixiaoyong.wanandroid.api.bean.DataProjectParam
import io.github.jixiaoyong.wanandroid.base.BaseViewModel
import io.github.jixiaoyong.wanandroid.data.NetWorkRepository
import io.github.jixiaoyong.wanandroid.data.PostBoundaryCallback
import io.github.jixiaoyong.wanandroid.data.Preference
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import io.github.jixiaoyong.wanandroid.utils.DatabaseUtils
import io.github.jixiaoyong.wanandroid.utils.jsonToListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-16
 * description: 项目
 */
class MoreViewModel(private val netWorkRepository: NetWorkRepository, action: Int, searchArgs: String?) : BaseViewModel() {

    private val mainTabsPreference: LiveData<Preference?> by lazy {
        DatabaseUtils.database
            .preferenceDao().queryPreferenceByKey(CommonConstants.PreferenceKey.WECHAT_TABS)
    }

    val mainTabs: LiveData<List<DataProjectParam>?>? = if (action == CommonConstants.Action.WECHAT) {
        map(mainTabsPreference) {
            formatStringToTabs(it?.value)
        }
    } else {
        null
    }

    val currentMainTabIndex = MutableLiveData(0)
    private val currentMainTabItem = MediatorLiveData<DataProjectParam?>()

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

    private fun updateCurrentTabItem(mainTabs: List<DataProjectParam>?, index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
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
                            viewModelScope.launch(Dispatchers.IO) {
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
                            viewModelScope.launch(Dispatchers.IO) {
                                netWorkRepository.getFavoritePostOnPage(currentPage)
                            }
                        }
                    )
            }
            CommonConstants.Action.SEARCH -> {
                DatabaseUtils.database
                    .baseArticlesDao().queryAllArticles(ApiCommondConstants.PostType.SearchPost).toLiveData(
                        pageSize = CommonConstants.Paging.PAGE_SIZE,
                        boundaryCallback = PostBoundaryCallback { currentPage ->
                            viewModelScope.launch(Dispatchers.IO) {
                                netWorkRepository.getSearchPostOnPage(
                                    searchArgs
                                        ?: "",
                                    currentPage
                                )
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
                                    viewModelScope.launch(Dispatchers.IO) {
                                        // wechat page start from 1
                                        netWorkRepository.getWechatPostOnPage(currentPage + 1, it.id)
                                    }
                                }
                            )
                    }
                }
            }
        }

    private fun formatStringToTabs(string: String?): List<DataProjectParam>? {
        return if (!string.isNullOrBlank()) {
            string.jsonToListOf()
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = netWorkRepository.getWechatList().execute().body()?.data
                    val jsonString = Gson().toJson(result)
                    val preference = Preference(CommonConstants.PreferenceKey.WECHAT_TABS, jsonString)
                    DatabaseUtils.database.preferenceDao().insert(preference)
                } catch (e: Exception) {
                    Logger.e("获取WECHAT tabs失败", e)
                }
            }
            null
        }
    }

    fun refreshWechatList() {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseUtils.database.baseArticlesDao().deleteAllArticles(ApiCommondConstants.PostType.WechatPost)
        }
    }
}
