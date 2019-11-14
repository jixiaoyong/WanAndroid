package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.base.BaseViewModel
import io.github.jixiaoyong.wanandroid.data.AccountRepository

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-13
 * description: todo
 */
class LoginAndRegisterViewModel(private val accountRepository: AccountRepository) : BaseViewModel() {

    val isLogin = MutableLiveData<Boolean>(true)

    suspend fun login(userName: String, password: String) = accountRepository.login(userName, password)

    fun register(userName: String, password: String, rePassword: String) {
        Logger.d("userName:$userName,password:$password,rePassword:$rePassword")
    }
}