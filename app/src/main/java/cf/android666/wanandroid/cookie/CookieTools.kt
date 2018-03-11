package cf.android666.wanandroid.cookie

import cf.android666.wanandroid.api.ApiUrl
import cf.android666.wanandroid.api.WanAndroidService
import com.google.gson.GsonBuilder
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by jixiaoyong on 2018/3/5.
 * email:jixiaoyong1995@gmail.com
 * 这个代码来自https://github.com/wangzailfm/WanAndroidClient
 */
object CookieTools{

    private  val TAG = "RetrofitHelper"
    private  val CONTENT_PRE = "OkHttp: "
    private  val SAVE_USER_LOGIN_KEY = "user/login"
    private  val SAVE_USER_REGISTER_KEY = "user/register"
    private  val SET_COOKIE_KEY = "set-cookie"
    private  val COOKIE_NAME = "Cookie"
    private  val CONNECT_TIMEOUT = 30L
    private  val READ_TIMEOUT = 10L
    private val INTERCEPTOR_ENABLE = false


    fun getCookieService(): WanAndroidService? {

        // okHttpClientBuilder
        val okHttpClientBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            // get response cookie
            addInterceptor {
                val request = it.request()
                val response = it.proceed(request)
                val requestUrl = request.url().toString()
                val domain = request.url().host()
                // set-cookie maybe has multi, login to save cookie
                if ((requestUrl.contains(SAVE_USER_LOGIN_KEY) || requestUrl.contains(
                                SAVE_USER_REGISTER_KEY
                        ))
                        && !response.headers(SET_COOKIE_KEY).isEmpty()) {
                    val cookies = response.headers(SET_COOKIE_KEY)
                    val cookie = encodeCookie(cookies)
                    saveCookie(requestUrl, domain, cookie)
                }
                response
            }
            // set request cookie
            addInterceptor {
                val request = it.request()
                val builder = request.newBuilder()
                val domain = request.url().host()
                // get domain cookie
                if (domain.isNotEmpty()) {
                    val spDomain: String by Preference(domain, "")
                    val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
                    if (cookie.isNotEmpty()) {
                        builder.addHeader(COOKIE_NAME, cookie)
                    }
                }
                it.proceed(builder.build())
            }
            // add log print
            if (INTERCEPTOR_ENABLE) {
                // loggingInterceptor
                addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                    Logger.e(TAG, CONTENT_PRE + it)
                }).apply {
                    // log level
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }

        return   Retrofit.Builder()
                .baseUrl(ApiUrl.baseUrl)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WanAndroidService::class.java)
    }


    /**
     * save cookie string
     */
    fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
                .map { cookie ->
                    cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                }
                .forEach {
                    it.filterNot { set.contains(it) }.forEach { set.add(it) }
                }

        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }

        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }

        return sb.toString()
    }

    /**
     * save cookie to SharePreferences
     */
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    private fun saveCookie(url: String?, domain: String?, cookies: String) {
        url ?: return
        var spUrl: String by Preference(url, cookies)
        @Suppress("UNUSED_VALUE")
        spUrl = cookies
        domain ?: return
        var spDomain: String by Preference(domain, cookies)
        @Suppress("UNUSED_VALUE")
        spDomain = cookies
    }


}