package io.github.jixiaoyong.wanandroid.viewmodel

import io.github.jixiaoyong.wanandroid.api.bean.DataHotKeyParam
import io.github.jixiaoyong.wanandroid.base.BaseViewModel
import io.github.jixiaoyong.wanandroid.data.NetWorkRepository

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-12-14
 * description: todo
 */
class SearchViewModel(private val netWorkRepository: NetWorkRepository) : BaseViewModel() {

    fun getHotSearchWords(): List<DataHotKeyParam> {
        return netWorkRepository.getHotSearchKeyList() ?: arrayListOf()
    }
}
