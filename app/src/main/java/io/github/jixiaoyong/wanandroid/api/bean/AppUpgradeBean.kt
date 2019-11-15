package io.github.jixiaoyong.wanandroid.api.bean


import com.google.gson.annotations.SerializedName

/**
 * Created by jixiaoyong on 2019/11/15.
 * email:jixiaoyong1995@gmail.com
 *
{
"errorCode": 1,
"appName": "WanAndroid",
"versionCode": 20191103,
"version": "1.2.0",
"summary": "WanAndroid版本更新到V1.2.0了！\n1.修复了无法加载数据的问题。\n2.适配了Android 9.0。\n3.其他优化。\n快来下载体验吧。",
"updateTime": "2019/11/03",
"url": "https://github.com/jixiaoyong/WanAndroid/releases/download/v1.2.0/WanAndroid-v1.2.0-release-20191103.apk"
}
 */
data class AppUpgradeBean(
        @SerializedName("appName")
        val appName: String,
        @SerializedName("errorCode")
        val errorCode: Int,
        @SerializedName("summary")
        val summary: String,
        @SerializedName("updateTime")
        val updateTime: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("version")
        val version: String,
        @SerializedName("versionCode")
        val versionCode: Int
)