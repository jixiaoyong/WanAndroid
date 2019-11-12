package io.github.jixiaoyong.wanandroid.api.bean

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: 项目分类 数据类
{
"children": [],
"courseId": 13,
"id": 294, // 该id在获取该分类下项目时需要用到
"name": "完整项目", // 该分类名称
"order": 145000,
"parentChapterId": 293,
"visible": 0
}
 */
data class DataProjectParam(
        var children: List<Any> = listOf(),
        var courseId: Int = 0, // 13
        var id: Int = 0, // 294
        var name: String = "", // 完整项目
        var order: Int = 0, // 145000
        var parentChapterId: Int = 0, // 293
        var visible: Int = 0 // 0
)