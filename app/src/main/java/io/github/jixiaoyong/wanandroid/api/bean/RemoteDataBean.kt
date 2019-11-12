package io.github.jixiaoyong.wanandroid.api.bean


import com.google.gson.annotations.SerializedName

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: WanAndroid返回数据基本结构
 */
data class RemoteDataBean<T>(
        @SerializedName("data")
        var `data`: T? = null,
        @SerializedName("errorCode")
        var errorCode: Int = 0, // 0
        @SerializedName("errorMsg")
        var errorMsg: String = ""
)