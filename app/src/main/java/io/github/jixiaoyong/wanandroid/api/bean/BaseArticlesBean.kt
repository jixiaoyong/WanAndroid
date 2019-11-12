package io.github.jixiaoyong.wanandroid.api.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by jixiaoyong on 2018/3/11.
 * email:jixiaoyong1995@gmail.com
 */
@Entity(tableName = "baseArticles")
data class BaseArticlesBean(
        /**
         * apkLink :
         * author : 小编
         * chapterId : 272
         * chapterName : 常用网站
         * collect : false
         * courseId : 13
         * desc :
         * envelopePic :
         * id : 1848
         * link : https://developers.google.cn/
         * niceDate : 2018-01-07
         * origin :
         * projectLink :
         * publishTime : 1515322795000
         * title : Google开发者
         * visible : 0
         * zan : 0
         */

        var apkLink: String = "",
        var author: String = "",
        var chapterId: Int = 0,
        var chapterName: String = "",
        var collect: Boolean = false,
        var courseId: Int = 0,
        var desc: String = "",
        var envelopePic: String = "",
        @PrimaryKey
        var id: Int = 0,
        var link: String = "",
        var niceDate: String = "",
        var origin: String = "",
        var projectLink: String = "",
        var publishTime: Long = 0,
        var title: String = "",
        var visible: Int = 0,
        var zan: Int = 0
//        @PrimaryKey(autoGenerate = true)
//        var _id: Int? = null
)