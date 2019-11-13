package io.github.jixiaoyong.wanandroid.utils

import io.github.jixiaoyong.wanandroid.viewmodel.LoginAndRegisterViewModel

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-13
 * description: todo
 */
object InjectUtils {

    fun provideLoginRegisterViewModel(): LoginAndRegisterViewModel {
        return LoginAndRegisterViewModel()
    }
}