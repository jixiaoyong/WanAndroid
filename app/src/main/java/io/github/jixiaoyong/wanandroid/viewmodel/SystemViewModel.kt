package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import cf.android666.applibrary.Logger
import com.google.gson.Gson
import io.github.jixiaoyong.wanandroid.api.bean.DataSystemParam
import io.github.jixiaoyong.wanandroid.base.BaseViewModel
import io.github.jixiaoyong.wanandroid.data.NetWorkRepository
import io.github.jixiaoyong.wanandroid.data.Preference
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import io.github.jixiaoyong.wanandroid.utils.DatabaseUtils
import io.github.jixiaoyong.wanandroid.utils.NetUtils
import io.github.jixiaoyong.wanandroid.utils.jsonToListOfDataSys

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-16
 * description: 体系页面
 */
class SystemViewModel(private val netWorkRepository: NetWorkRepository) : BaseViewModel() {

    val netState = MutableLiveData<NetUtils.NetworkState>(NetUtils.NetworkState.Normal)

    /**
     * 需要执行在后台执行，可能会有数据库或者网络请求
     */
    fun mainTabs(): List<DataSystemParam<DataSystemParam<Any>>>? {
        netState.postValue(NetUtils.NetworkState.Loading)
        val dataJson = DatabaseUtils.database
            .preferenceDao().queryPreferenceByKeySync(CommonConstants.PreferenceKey.SYSTEM_TABS)
        return formatStringToTabs(dataJson?.value)
    }

    private fun formatStringToTabs(string: String?): List<DataSystemParam<DataSystemParam<Any>>>? {
        return if (!string.isNullOrBlank()) {
            val result = string.jsonToListOfDataSys()
            netState.postValue(NetUtils.NetworkState.Succeeded)
            result
        } else {
            try {
                val result = netWorkRepository.getMainSystemList().execute().body()?.data
                val jsonString = Gson().toJson(result)
                val preference = Preference(CommonConstants.PreferenceKey.SYSTEM_TABS, jsonString)
                DatabaseUtils.database.preferenceDao().insert(preference)
                netState.postValue(NetUtils.NetworkState.Succeeded)
            } catch (e: Exception) {
                Logger.e("获取SYS tabs失败", e)
                netState.postValue(NetUtils.NetworkState.Error)
            }
            null
        }
    }
}
