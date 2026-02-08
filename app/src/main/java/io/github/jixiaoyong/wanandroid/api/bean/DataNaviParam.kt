package io.github.jixiaoyong.wanandroid.api.bean

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: 体系结构 数据类
{
"articles":ArrayOf[DataIndexPostParam],
"cid":272,
"name":"常用网站"
}
 */
data class DataNaviParam(
        var articles: List<DataIndexPostParam> = listOf(),
        var cid: Int = 0, // 272
        var name: String = "" // 常用网站
)