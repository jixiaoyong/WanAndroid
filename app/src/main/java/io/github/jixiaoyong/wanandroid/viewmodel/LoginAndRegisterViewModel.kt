package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.base.BaseViewModel

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-13
 * description: todo
 */
class LoginAndRegisterViewModel : BaseViewModel() {

    val isLogin = MutableLiveData<Boolean>(true)

    fun login(userName: String, password: String) {
        Logger.d("userName:$userName,password:$password")
    }

    fun register(userName: String, password: String, rePassword: String) {
        Logger.d("userName:$userName,password:$password,rePassword:$rePassword")
    }
}