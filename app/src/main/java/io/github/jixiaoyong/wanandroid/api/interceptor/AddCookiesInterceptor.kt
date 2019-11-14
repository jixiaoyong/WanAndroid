package io.github.jixiaoyong.wanandroid.api.interceptor

import android.util.Log
import io.github.jixiaoyong.wanandroid.api.WanAndroidService
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
class AddCookiesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        val hostName = request.url().host() ?: WanAndroidService.BASE_URL
        val cookiesList = DatabaseUtils.database.cookiesDao().queryAllCookies()
        val cookies = cookiesList.find {
            it.hostName == hostName
        }
        cookies?.cookiesStr?.split(",")?.forEach {
            requestBuilder.addHeader("cookie", it)
            Log.d("TAG_W", "add cookies:$it")

        }

        return chain.proceed(requestBuilder.build())
    }
}