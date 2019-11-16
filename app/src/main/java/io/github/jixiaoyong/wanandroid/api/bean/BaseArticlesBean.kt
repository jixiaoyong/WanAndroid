package io.github.jixiaoyong.wanandroid.api.bean


/**
 * Created by jixiaoyong on 2018/3/11.
 * email:jixiaoyong1995@gmail.com
 */
data class BaseArticlesBean(
        /**
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

        var apkLink: String = "",
        var author: String = "",
        var chapterId: Int = 0,
        var chapterName: String = "",
        var collect: Boolean = false,
        var courseId: Int = 0,
        var desc: String = "",
        var envelopePic: String = "",
        var id: Int = 0,
        var link: String = "",
        var niceDate: String = "",
        var origin: String = "",
        var projectLink: String = "",
        var publishTime: Long = 0,
        var title: String = "",
        var visible: Int = 0,
        var zan: Int = 0
)