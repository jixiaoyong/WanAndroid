package io.github.jixiaoyong.wanandroid.api.interceptor

import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.data.CookiesBean
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import io.github.jixiaoyong.wanandroid.utils.DatabaseUtils
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.*

/**
 *  Created by jixiaoyong1995@gmail.com
 *  Data: 2019/11/15.
 *  Description:
 */
class CookieManager {

    private val cookieMap: HashMap<String, List<Cookie>> = hashMapOf()
    private val separator = "_SEPARATOR_"

    val cookieJar = object : CookieJar {
        override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
            val loginUrl = CommonConstants.WebSites.BASE_URL_WANANDROID + "user/login"
            val registerUrl = CommonConstants.WebSites.BASE_URL_WANANDROID + "user/register"
            val requestUrl = url.url().toString()
            if (requestUrl == loginUrl || requestUrl == registerUrl) {
                val hostName = url.host()
                cookieMap[hostName] = cookies
                val loginUserNameCookie = cookies.find {
                    it.name() == "loginUserName"
                }
                Logger.d("hostName:$hostName loginUserName:${loginUserNameCookie?.value()},expiresAt:${loginUserNameCookie?.expiresAt()}")
                DatabaseUtils.database.cookiesDao().insert(CookiesBean(
                        hostName,
                        cookies.joinToString(separator = separator) {
                            it.toString()
                        },
                        loginUserNameCookie?.value(),
                        loginUserNameCookie?.expiresAt() ?: System.currentTimeMillis()
                ))
            }
        }

        override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
            val hostName = url.host()
            return if (cookieMap.containsKey(hostName)) {
                cookieMap[hostName]?.toMutableList() ?: mutableListOf()
            } else {
                val cookiesStr = DatabaseUtils.database.cookiesDao().queryAllCookies().find {
                    it.hostName == hostName
                }?.cookiesStr
                cookiesStr?.split(separator)?.map {
                    //JSESSIONID=F2EC1876726B14EF3EAF26C172FA2676; Path=/; Secure; HttpOnly;
// Expires=Sun, 15-Dec-2019 01:46:33 GMT; Domain=wanandroid.com;

                    var (cName, cValue) = Pair("", "")
                    val cookieStrMap = it.split(";").mapIndexed { index, cookieItem ->
                        val key = cookieItem.substringBefore("=")
                        val value = cookieItem.substringAfter("=")
                        if (index == 0) {
                            cName = key
                            cValue = value
                        }
                        key to value
                    }.toMap()

                    Cookie.Builder()
                            .name(cName)
                            .value(cValue)
                            .path(cookieStrMap.getOrElse("Path", { "/" }))
                            .domain(cookieStrMap.getOrElse("Domain", { "wanandroid.com" }))
                            .expiresAt(Date.parse(cookieStrMap.getOrElse("Expires",
                                    { Date(System.currentTimeMillis()).toString() })))
                            .build()
                }?.toMutableList() ?: mutableListOf()
            }

        }
    }
}