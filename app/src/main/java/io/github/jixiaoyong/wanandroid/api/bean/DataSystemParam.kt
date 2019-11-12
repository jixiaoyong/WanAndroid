package io.github.jixiaoyong.wanandroid.api.bean

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: 体系结构 数据类
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

data class DataSystemParam<T>(
        var children: List<T> = listOf(),
        var courseId: Int = 0, // 13
        var id: Int = 0, // 150
        var name: String = "", // 开发环境
        var order: Int = 0, // 1
        var parentChapterId: Int = 0, // 0
        var userControlSetTop: Boolean = false, // false
        var visible: Int = 0 // 1
)

