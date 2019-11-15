package io.github.jixiaoyong.wanandroid.data

import io.github.jixiaoyong.wanandroid.utils.DatabaseUtils
import io.github.jixiaoyong.wanandroid.utils.NetUtils

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-14
 * description: todo
 */
class AccountRepository {

    suspend fun login(userName: String, password: String) = NetUtils.wanAndroidApi.login(userName, password)

    fun getCookieBean() = DatabaseUtils.database.cookiesDao().queryAllCookiesAsync()

    suspend fun getCoinInfo() = NetUtils.wanAndroidApi.getCoinInfo()
}