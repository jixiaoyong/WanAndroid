package io.github.jixiaoyong.wanandroid.utils

import io.github.jixiaoyong.wanandroid.data.AccountRepository
import io.github.jixiaoyong.wanandroid.data.NetWorkRepository
import io.github.jixiaoyong.wanandroid.viewmodel.*

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
        return MainViewModelFactory(AccountRepository(), NetWorkRepository())
    }

    fun provideAboutViewModelFactory(): AboutViewModelFactory {
        return AboutViewModelFactory(NetWorkRepository())
    }

    fun provideSystemViewModelFactory(): SystemViewModelFactory {
        return SystemViewModelFactory(NetWorkRepository())
    }

    fun provideProjectViewModelFactory(): ProjectViewModelFactory {
        return ProjectViewModelFactory(NetWorkRepository())
    }
}