package io.github.jixiaoyong.wanandroid.api.bean

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: 积分获取历史 数据类
{
"coinCount": 24,
"date": 1573522287000,
"desc": "2019-11-12 09:31:27 签到 , 积分：10 + 14",
"id": 90208,
"reason": "签到",
"type": 1,
"userId": 2917,
"userName": "2675995617@qq.com"
}
 */
data class DataCoinHistoryParam(
        var coinCount: Int = 0, // 24
        var date: Long = 0, // 1573522287000
        var desc: String = "", // 2019-11-12 09:31:27 签到 , 积分：10 + 14
        var id: Int = 0, // 90208
        var reason: String = "", // 签到
        var type: Int = 0, // 1
        var userId: Int = 0, // 2917
        var userName: String = "" // 2675995617@qq.com
)