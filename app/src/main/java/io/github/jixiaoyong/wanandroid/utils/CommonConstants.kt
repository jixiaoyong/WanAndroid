package io.github.jixiaoyong.wanandroid.utils

/**
 *  Created by jixiaoyong1995@gmail.com
 *  Data: 2019/11/15.
 *  Description: 通用的一些常量
 */
object CommonConstants {

    const val ACTION_URL = "url"

    object WebSites {
        const val APP_URL = "https://github.com/jixiaoyong/WanAndroid"
        const val AUTHOR_URL = "https://jixiaoyong.github.io/"
        const val UPGRADE_URL = "https://jixiaoyong.github.io/download/data/wanandroid/updateInfo.json"

        const val BASE_URL_WANANDROID = "https://www.wanandroid.com/"
        const val BASE_URL_UPGRADE = "https://jixiaoyong.github.io/"
    }

    object Paging {
        const val ENABLE_PLACEHOLDERS: Boolean = true
        const val PAGE_SIZE = 20
    }

    object Action {

        const val KEY = "action"

        const val WECHAT = 0
        const val FAVORITE = 1
        const val PEOPLE = 2
    }
}