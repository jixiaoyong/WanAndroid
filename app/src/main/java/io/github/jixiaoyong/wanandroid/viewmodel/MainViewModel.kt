package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.liveData
import io.github.jixiaoyong.wanandroid.base.BaseViewModel
import io.github.jixiaoyong.wanandroid.data.AccountRepository

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: todo
 */
class MainViewModel(private val accountRepository: AccountRepository) : BaseViewModel() {

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

}