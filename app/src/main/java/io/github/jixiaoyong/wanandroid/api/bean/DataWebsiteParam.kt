package io.github.jixiaoyong.wanandroid.api.bean

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: 项目分类 数据类
{
"desc": "",
"icon": "",
"id": 3497,
"link": "muggleapp.tk",
"name": "MuggleApp",
"order": 0,
"userId": 2917,
"visible": 1
}
 */
data class DataWebsiteParam(
        var desc: String = "",
        var icon: String = "",
        var id: Int = 0, // 3497
        var link: String = "", // muggleapp.tk
        var name: String = "", // MuggleApp
        var order: Int = 0, // 0
        var userId: Int = 0, // 2917
        var visible: Int = 0 // 1
)