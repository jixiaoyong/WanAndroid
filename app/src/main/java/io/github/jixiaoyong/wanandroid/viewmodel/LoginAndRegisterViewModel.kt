package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.MediatorLiveData
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

    val nameInputLength = MutableLiveData(0)
    val pwdInputLength = MutableLiveData(0)
    val repwdInputLength = MutableLiveData(0)

    val canButtonEnable = MediatorLiveData<Boolean>()

    init {
        canButtonEnable.addSource(nameInputLength) {
            checkCanButtonEnable(it, pwdInputLength.value, isLogin.value, repwdInputLength.value)
        }
        canButtonEnable.addSource(pwdInputLength) {
            checkCanButtonEnable(nameInputLength.value, it, isLogin.value, repwdInputLength.value)
        }
        canButtonEnable.addSource(repwdInputLength) {
            checkCanButtonEnable(nameInputLength.value, pwdInputLength.value, isLogin.value, it)
        }
        canButtonEnable.addSource(isLogin) {
            checkCanButtonEnable(nameInputLength.value, pwdInputLength.value, it, repwdInputLength.value)
        }

    }

    private fun checkCanButtonEnable(nameLength: Int?, pwdInputLength: Int?, isLogin: Boolean? = true, repwdInputLength: Int? = 6) {
        Logger.d("nameLength:$nameLength, pwdInputLength:$pwdInputLength, isLogin: $isLogin, repwdInputLength:$repwdInputLength")
        canButtonEnable.value = if (isLogin == true) {
            nameLength ?: 0 >= 2 && pwdInputLength ?: 0 >= 6
        } else {
            nameLength ?: 0 >= 2 && pwdInputLength ?: 0 >= 6 && repwdInputLength ?: 0 >= 6
        }
    }

    suspend fun login(userName: String, password: String) = accountRepository.login(userName, password)

    fun register(userName: String, password: String, rePassword: String) {
        Logger.d("userName:$userName,password:$password,rePassword:$rePassword")
    }
}