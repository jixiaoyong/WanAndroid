package io.github.jixiaoyong.wanandroid.api.bean

/**
 * Created by jixiaoyong on 2018/3/8.
 * email:jixiaoyong1995@gmail.com
 */
class CookieBean {
    /**
     * data : {"collectIds":[-1,-1,-1,-1,-1,-1,-1],"email":"","icon":"","id":,"password":"","type":0,"username":""}
     * errorCode : 0
     * errorMsg :
     */

    var data: DataBean = DataBean()
    var errorCode: Int = 0
    var errorMsg: String = ""


    class DataBean {
        /**
         * collectIds : [-1,-1,-1,-1,-1,-1,-1]
         * email :
         * icon :
         * id :
         * password :
         * type : 0
         * username :
         */

        var email: String = ""
        var icon: String = ""
        var id: Int = 0
        var password: String = ""
        var type: Int = 0
        var username: String = ""
        var collectIds: List<Int>? = null
    }
}