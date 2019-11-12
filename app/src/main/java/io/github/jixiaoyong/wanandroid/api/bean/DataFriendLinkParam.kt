package io.github.jixiaoyong.wanandroid.api.bean


import com.google.gson.annotations.SerializedName

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: 常用网站 数据类
{
"icon": "",
"id": 17,
"link": "http://www.wanandroid.com/article/list/0?cid=176",
"name": "国内大牛博客集合",
"order": 1,
"visible": 1
}
 */
data class DataFriendLinkParam(
        @SerializedName("icon")
        var icon: String = "",
        @SerializedName("id")
        var id: Int = 0, // 17
        @SerializedName("link")
        var link: String = "", // http://www.wanandroid.com/article/list/0?cid=176
        @SerializedName("name")
        var name: String = "", // 国内大牛博客集合
        @SerializedName("order")
        var order: Int = 0, // 1
        @SerializedName("visible")
        var visible: Int = 0 // 1
)