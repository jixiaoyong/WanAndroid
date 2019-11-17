package io.github.jixiaoyong.wanandroid.api.bean


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import io.github.jixiaoyong.wanandroid.api.ApiCommondConstants

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: 首页文章数据类
{
"apkLink": "",
"audit": 1,
"author": "",
"chapterId": 502,
"chapterName": "自助",
"collect": false,
"courseId": 13,
"desc": "",
"envelopePic": "",
"fresh": true,
"id": 10239,
"link": "https://mp.weixin.qq.com/s/dhc3povL-_hVtp10IrRMow",
"niceDate": "2小时前",
"niceShareDate": "2小时前",
"origin": "",
"prefix": "",
"projectLink": "",
"publishTime": 1573559091000,
"selfVisible": 0,
"shareDate": 1573559091000,
"shareUser": "winlee28",
"superChapterId": 494,
"superChapterName": "广场Tab",
"tags": [
{
"name": "导航",
"url": "/navi#272"
}
],
"title": "Flutter混合开发(二)：iOS项目集成Flutter模块详细指南",
"type": 0,
"userId": 25211,
"visible": 1,
"zan": 0
}
 */
@TypeConverters(TagConvert::class)
@Entity(tableName = "baseArticles")
data class DataIndexPostParam(
        @SerializedName("apkLink")
        var apkLink: String = "",
        @SerializedName("audit")
        var audit: Int = 0, // 1
        @SerializedName("author")
        var author: String = "",//作者
        @SerializedName("chapterId")
        var chapterId: Int = 0, // 502,子体系 System
        @SerializedName("chapterName")
        var chapterName: String = "", // 自助
        @SerializedName("collect")
        var collect: Boolean = false, // false,是否收藏
        @SerializedName("courseId")
        var courseId: Int = 0, // 13
        @SerializedName("desc")
        var desc: String = "",//描述，内容节略，大多为空
        @SerializedName("envelopePic")
        var envelopePic: String = "",
        @SerializedName("fresh")
        var fresh: Boolean = false, // true,是否为最新的文章NEW
        @SerializedName("id")
        var id: Int = 0, // 10239
        @SerializedName("link")
        var link: String = "", // https://mp.weixin.qq.com/s/dhc3povL-_hVtp10IrRMow
        @SerializedName("niceDate")
        var niceDate: String = "", // 2小时前
        @SerializedName("niceShareDate")
        var niceShareDate: String = "", // 2小时前
        @SerializedName("origin")
        var origin: String = "",
        @SerializedName("prefix")
        var prefix: String = "",
        @SerializedName("projectLink")
        var projectLink: String = "",
        @SerializedName("publishTime")
        var publishTime: Long = 0, // 1573559091000
        @SerializedName("selfVisible")
        var selfVisible: Int = 0, // 0
        @SerializedName("shareDate")
        var shareDate: Long = 0, // 1573559091000
        @SerializedName("shareUser")
        var shareUser: String = "", // winlee28 分享人名称
        @SerializedName("superChapterId")
        var superChapterId: Int = 0, // 494，父体系
        @SerializedName("superChapterName")
        var superChapterName: String = "", // 广场Tab
        @SerializedName("tags")
        var tags: List<Tag> = listOf(),
        @SerializedName("title")
        var title: String = "", // Flutter混合开发(二)：iOS项目集成Flutter模块详细指南
        @SerializedName("type")
        var type: Int = 0, // 0
        @SerializedName("userId")
        var userId: Int = 0, // 25211 分享人ID
        @SerializedName("visible")
        var visible: Int = 0, // 1
        @SerializedName("zan")
        var zan: Int = 0, // 0
        @PrimaryKey(autoGenerate = true)
        var _idDB: Int? = null,
        var _postType: Int = ApiCommondConstants.PostType.IndexPost
) {
    data class Tag(
            @SerializedName("name")
            var name: String = "", // 导航
            @SerializedName("url")
            var url: String = "" // /navi#272
    )
}

class TagConvert {

    @TypeConverter
    fun stringToObject(value: String): List<DataIndexPostParam.Tag> {
        val listType = object : TypeToken<List<DataIndexPostParam.Tag>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<DataIndexPostParam.Tag>): String {
        val gson = Gson()
        return gson.toJson(list)
    }


}