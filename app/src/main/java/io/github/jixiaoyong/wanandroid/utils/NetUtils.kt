package io.github.jixiaoyong.wanandroid.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.androidbrowserhelper.trusted.TwaLauncher
import com.google.androidbrowserhelper.trusted.TwaProviderPicker
import io.github.jixiaoyong.wanandroid.activity.ContentActivity
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

    sealed class NetworkState {
        object Succeeded : NetworkState()
        object Normal : NetworkState()
        object Loading : NetworkState()
        object Error : NetworkState()

    }

    /**
     * 有Chrome适配模式的话就用，否则就使用自己的WebView打开页面
     */
    fun loadUrl(context: Context, urlStr: String) {
        val url = Uri.parse(urlStr)
        if (TwaProviderPicker.pickProvider(context.packageManager).launchMode
                == TwaProviderPicker.LaunchMode.BROWSER) {
            val twaLauncher = TwaLauncher(context)
            twaLauncher.launch(url)
        } else {
            val intent = Intent(context, ContentActivity::class.java)
            intent.putExtra(CommonConstants.ACTION_URL, urlStr)
            context.startActivity(intent)
        }
    }
}