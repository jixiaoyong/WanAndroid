package io.github.jixiaoyong.wanandroid.api.bean


import com.google.gson.annotations.SerializedName

data class UpdateInfoBean(@SerializedName("summary")
                          val summary: String = "",
                          @SerializedName("appName")
                          val appName: String = "",
                          @SerializedName("errorCode")
                          val errorCode: Int = 0,
                          @SerializedName("updateTime")
                          val updateTime: String = "",
                          @SerializedName("version")
                          val version: String = "",
                          @SerializedName("versionCode")
                          val versionCode: Int = 0,
                          @SerializedName("url")
                          val url: String = "")

// online url
// http://jixiaoyong.github.io/download/data/wanandroid/updateInfo.json

//{
//    "errorCode":1,
//    "appName": "WanAndroid",
//    "versionCode": 1,
//    "version": "0.1-beta",
//    "summary": "更新说明",
//    "updateTime": "2018/03/11",
//    "url": "https://raw.githubusercontent.com/jixiaoyong/Notes-Files/master/download/apk/WanAndroid.apk"
//}

