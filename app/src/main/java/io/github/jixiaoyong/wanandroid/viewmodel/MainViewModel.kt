package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.api.bean.DataBannerParam
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam
import io.github.jixiaoyong.wanandroid.base.BaseViewModel
import io.github.jixiaoyong.wanandroid.data.AccountRepository
import io.github.jixiaoyong.wanandroid.data.IndexPostPagingSource
import io.github.jixiaoyong.wanandroid.data.NetWorkRepository
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: todo
 */
class MainViewModel(
    private val accountRepository: AccountRepository,
    private val netWorkRepository: NetWorkRepository
) : BaseViewModel() {

    init {
        Logger.e("this should be call just once")
    }

    val bannerListLiveData = MutableLiveData<List<DataBannerParam>?>()
    val cookies = accountRepository.getCookieBean()

    val isLogin = map(cookies) {
        val cookie = it?.getOrNull(0)
        cookie != null && cookie.expirationDate >= System.currentTimeMillis()
    }

    val coinInfo = switchMap(isLogin) {
        liveData {
            if (it) {
                val result = accountRepository.getCoinInfo().data
                emit(result)
            } else {
                emit(null)
            }
        }
    }

    val allIndexPost = Pager(
        PagingConfig(CommonConstants.Paging.PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { IndexPostPagingSource(netWorkRepository) }
    ).flow.cachedIn(this)

    fun getBanner() {
        viewModelScope.launch(Dispatchers.IO) {
            Logger.d("开始请求BannerView 数据")
            val bannerList = netWorkRepository.getBannerListSync()
            bannerListLiveData.postValue(bannerList)
        }
    }

    fun updateIndexPostCollectState(dataIndexPostParam: DataIndexPostParam) {
        netWorkRepository.updatePostCollectState(dataIndexPostParam)
    }

    fun isLogin(): Boolean {
        return isLogin.value == true
    }
}
