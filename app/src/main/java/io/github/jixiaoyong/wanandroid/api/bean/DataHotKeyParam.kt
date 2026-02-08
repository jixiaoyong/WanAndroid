package io.github.jixiaoyong.wanandroid.api.bean


import com.google.gson.annotations.SerializedName

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: 搜索热词 数据类
{
"id": 4,
"link": "",
"name": "Camera 相机",
"order": 6,
"visible": 1
}
 */
data class DataHotKeyParam(
        @SerializedName("id")
        var id: Int = 0, // 6
        @SerializedName("link")
        var link: String = "",
        @SerializedName("name")
        var name: String = "", // 面试
        @SerializedName("order")
        var order: Int = 0, // 1
        @SerializedName("visible")
        var visible: Int = 0 // 1
)