package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam
import io.github.jixiaoyong.wanandroid.base.BaseViewModel
import io.github.jixiaoyong.wanandroid.data.NetWorkRepository
import io.github.jixiaoyong.wanandroid.data.PostPagingSource
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2021-03-11
 * description: todo
 */
class ListViewModel(private val netWorkRepository: NetWorkRepository) : BaseViewModel() {

    fun getSystemList(cid: Int): Flow<PagingData<DataIndexPostParam>> {
        return Pager(
            PagingConfig(CommonConstants.Paging.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                PostPagingSource { page ->
                    netWorkRepository.getSystemPostOnPage(page, cid)
                }
            }
        ).flow.cachedIn(this)
    }

    fun updateISystemPostCollectState(systemPostParam: DataIndexPostParam) {
        viewModelScope.launch(Dispatchers.IO) { netWorkRepository.updatePostCollectState(systemPostParam) }
    }
}
