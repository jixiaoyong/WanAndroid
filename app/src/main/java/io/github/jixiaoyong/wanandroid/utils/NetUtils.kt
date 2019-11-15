package io.github.jixiaoyong.wanandroid.utils

import android.content.Context
import android.net.Uri
import com.google.androidbrowserhelper.trusted.TwaLauncher
import io.github.jixiaoyong.wanandroid.api.AppUpgradeApi
import io.github.jixiaoyong.wanandroid.api.WanAndroidService
import io.github.jixiaoyong.wanandroid.api.interceptor.CookieManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-14
 * description: todo
 */
object NetUtils {

    lateinit var wanAndroidApi: WanAndroidService
    lateinit var appUpgradeApi: AppUpgradeApi

    const val TIME_OUT_DURATION = 20L

    fun init(applicationContext: Context) {
        val httpClient = OkHttpClient.Builder()
                .readTimeout(TIME_OUT_DURATION, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_DURATION, TimeUnit.SECONDS)
                .cookieJar(CookieManager().cookieJar)
                .build()

        val retrofit = Retrofit.Builder()
                .client(httpClient)
                .baseUrl(CommonConstants.WebSites.BASE_URL_WANANDROID)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        val upgradeRetrofit = Retrofit.Builder()
                .client(httpClient)
                .baseUrl(CommonConstants.WebSites.BASE_URL_UPGRADE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        wanAndroidApi = retrofit.create(WanAndroidService::class.java)
        appUpgradeApi = upgradeRetrofit.create(AppUpgradeApi::class.java)

    }

    object ErrorCode {
        const val SUCCEEDED = 0
    }

    fun loadUrl(context: Context, urlStr: String) {
        val url = Uri.parse(urlStr)
        TwaLauncher(context).launch(url)
    }
}