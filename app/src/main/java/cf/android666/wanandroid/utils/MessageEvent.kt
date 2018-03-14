package cf.android666.wanandroid.utils

/**
 * Created by jixiaoyong on 2018/3/7.
 * email:jixiaoyong1995@gmail.com
 */
object MessageEvent{

    var isLogin = false

    var isNightMode = false

    var favoriteCount = 0

    var isCollectChanged = false

    var userName = ""


    fun getInstance():MessageEvent{
        return this
    }

    fun setIsLogin(isLo: Boolean): MessageEvent {
        isLogin = isLo
        return this
    }

    fun setIsCollectChanged(isLo: Boolean): MessageEvent {
        this.isCollectChanged = isLo
        return this
    }


}