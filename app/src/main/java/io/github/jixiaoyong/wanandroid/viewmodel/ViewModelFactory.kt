package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.jixiaoyong.wanandroid.data.AccountRepository
import io.github.jixiaoyong.wanandroid.data.NetWorkRepository

/**
 *  Created by jixiaoyong1995@gmail.com
 *  Data: 2019/11/15.
 *  Description: ViewModelFactory
 */
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val accountRepository: AccountRepository,
                           private val netWorkRepository: NetWorkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(accountRepository, netWorkRepository) as T
    }
}

@Suppress("UNCHECKED_CAST")
class LoginRegisterViewModelFactory(private val accountRepository: AccountRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginAndRegisterViewModel(accountRepository) as T
    }
}

@Suppress("UNCHECKED_CAST")
class AboutViewModelFactory(private val netWorkRepository: NetWorkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AboutViewModel(netWorkRepository) as T
    }
}

@Suppress("UNCHECKED_CAST")
class SystemViewModelFactory(private val netWorkRepository: NetWorkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SystemViewModel(netWorkRepository) as T
    }
}

@Suppress("UNCHECKED_CAST")
class ProjectViewModelFactory(private val netWorkRepository: NetWorkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectViewModel(netWorkRepository) as T
    }
}