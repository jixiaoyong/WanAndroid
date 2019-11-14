package io.github.jixiaoyong.wanandroid.api.interceptor

import android.util.Log
import io.github.jixiaoyong.wanandroid.api.WanAndroidService
import io.github.jixiaoyong.wanandroid.data.CookiesBean
import io.github.jixiaoyong.wanandroid.utils.DatabaseUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-14
 * description: todo
 */
class SaveCookiesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val hostName = response.request().url().host() ?: WanAndroidService.BASE_URL
        val cookies = response.headers("set-cookie").toTypedArray().joinToString(",")
        DatabaseUtils.database.cookiesDao().cleanAll()
        DatabaseUtils.database.cookiesDao().insert(CookiesBean(hostName, cookies))
        Log.d("TAG_W", "save cookies:$cookies")
        return response
    }
}