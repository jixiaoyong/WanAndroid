package io.github.jixiaoyong.wanandroid.utils

import io.github.jixiaoyong.wanandroid.data.AccountRepository
import io.github.jixiaoyong.wanandroid.data.NetWorkRepository
import io.github.jixiaoyong.wanandroid.viewmodel.AboutViewModelFactory
import io.github.jixiaoyong.wanandroid.viewmodel.LoginRegisterViewModelFactory
import io.github.jixiaoyong.wanandroid.viewmodel.MainViewModelFactory

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-13
 * description: todo
 */
object InjectUtils {

    fun provideLoginRegisterViewModelFactory(): LoginRegisterViewModelFactory {
        return LoginRegisterViewModelFactory(AccountRepository())
    }


    fun provideMainViewModelFactory(): MainViewModelFactory {
        return MainViewModelFactory(AccountRepository())
    }

    fun provideAboutViewModelFactory(): AboutViewModelFactory {
        return AboutViewModelFactory(NetWorkRepository())
    }
}