package io.github.jixiaoyong.wanandroid.api.bean


import com.google.gson.annotations.SerializedName

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: 首页Banner 数据类
{
"desc": "",
"id": 6,
"imagePath": "https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png",
"isVisible": 1,
"order": 1,
"title": "我们新增了一个常用导航Tab~",
"type": 1,
"url": "https://www.wanandroid.com/navi"
},
 */
data class DataBannerParam(
        @SerializedName("desc")
        var desc: String = "",//描述内容
        @SerializedName("id")
        var id: Int = 0, // 20
        @SerializedName("imagePath")
        var imagePath: String = "", // https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png
        @SerializedName("isVisible")
        var isVisible: Int = 0, // 1
        @SerializedName("order")
        var order: Int = 0, //顺序，从0开始 2
        @SerializedName("title")
        var title: String = "", //标题  flutter 中文社区
        @SerializedName("type")
        var type: Int = 0, // 1
        @SerializedName("url")
        var url: String = "" //点击后跳转链接 https://flutter.cn/
)