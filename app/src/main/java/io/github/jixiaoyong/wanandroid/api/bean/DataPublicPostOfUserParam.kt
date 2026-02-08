package io.github.jixiaoyong.wanandroid.api.bean

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: 项目分类 数据类

"coinInfo": { // 该用户积分信息
"coinCount": 20, // 积分总数
"rank": 1, // 排名
"userId": 2,
"username": "x**oyang"
},
"shareArticles": { // 该用户分享文章分页信息
}
}

 */

data class DataPublicPostOfUserParam(
        var coinInfo: DataCoinParam = DataCoinParam(),
        var shareArticles: DataPageOf<DataIndexPostParam> = DataPageOf()
)