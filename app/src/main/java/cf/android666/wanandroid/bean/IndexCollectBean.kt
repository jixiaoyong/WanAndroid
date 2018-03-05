package cf.android666.wanandroid.bean

/**
 * Created by jixiaoyong on 2018/3/5.
 * email:jixiaoyong1995@gmail.com
 */
class IndexCollectBean{


    /**
     * data : {"curPage":1,"datas":[{"author":"星辰之力","chapterId":0,"chapterName":"","courseId":13,"desc":"","envelopePic":"","id":4040,"link":"https://www.cnblogs.com/zhujiabin/p/6207913.html","niceDate":"24分钟前","origin":"","originId":-1,"publishTime":1520259116000,"title":"Android 使用Okhttp/Retrofit持久化cookie的简便方式 - 星辰之力 - 博客园","userId":2917,"visible":0,"zan":0},{"author":"Tamic大白","chapterId":0,"chapterName":"","courseId":13,"desc":"","envelopePic":"","id":4023,"link":"http://blog.csdn.net/sk719887916/article/details/51700659","niceDate":"49分钟前","origin":"","originId":-1,"publishTime":1520257600000,"title":"Retrofit2.0 ，OkHttp3完美同步持久Cookie实现免登录(二)","userId":2917,"visible":0,"zan":0},{"author":"sheepm","chapterId":0,"chapterName":"","courseId":13,"desc":"","envelopePic":"","id":3536,"link":"https://www.jianshu.com/p/031745744bfa","niceDate":"2018-03-01","origin":"","originId":-1,"publishTime":1519918584000,"title":"RxJava/RxAndroid 使用实例实践","userId":2917,"visible":0,"zan":0}],"offset":0,"over":true,"pageCount":1,"size":20,"total":3}
     * errorCode : 0
     * errorMsg :
     */

     var data: DataBean = DataBean()
     var errorCode: Int = 0
     var errorMsg: String = ""


    class DataBean {
        /**
         * curPage : 1
         * datas : [{"author":"星辰之力","chapterId":0,"chapterName":"","courseId":13,"desc":"","envelopePic":"","id":4040,"link":"https://www.cnblogs.com/zhujiabin/p/6207913.html","niceDate":"24分钟前","origin":"","originId":-1,"publishTime":1520259116000,"title":"Android 使用Okhttp/Retrofit持久化cookie的简便方式 - 星辰之力 - 博客园","userId":2917,"visible":0,"zan":0},{"author":"Tamic大白","chapterId":0,"chapterName":"","courseId":13,"desc":"","envelopePic":"","id":4023,"link":"http://blog.csdn.net/sk719887916/article/details/51700659","niceDate":"49分钟前","origin":"","originId":-1,"publishTime":1520257600000,"title":"Retrofit2.0 ，OkHttp3完美同步持久Cookie实现免登录(二)","userId":2917,"visible":0,"zan":0},{"author":"sheepm","chapterId":0,"chapterName":"","courseId":13,"desc":"","envelopePic":"","id":3536,"link":"https://www.jianshu.com/p/031745744bfa","niceDate":"2018-03-01","origin":"","originId":-1,"publishTime":1519918584000,"title":"RxJava/RxAndroid 使用实例实践","userId":2917,"visible":0,"zan":0}]
         * offset : 0
         * over : true
         * pageCount : 1
         * size : 20
         * total : 3
         */

        var curPage: Int = 0
        var offset: Int = 0
        var isOver: Boolean = false
        var pageCount: Int = 0
        var size: Int = 0
        var total: Int = 0
        var datas: ArrayList<DatasBean> = arrayListOf()

        class DatasBean {
            /**
             * author : 星辰之力
             * chapterId : 0
             * chapterName :
             * courseId : 13
             * desc :
             * envelopePic :
             * id : 4040
             * link : https://www.cnblogs.com/zhujiabin/p/6207913.html
             * niceDate : 24分钟前
             * origin :
             * originId : -1
             * publishTime : 1520259116000
             * title : Android 使用Okhttp/Retrofit持久化cookie的简便方式 - 星辰之力 - 博客园
             * userId : 2917
             * visible : 0
             * zan : 0
             */

            var author: String? = ""
            var chapterId: Int = 0
            var chapterName: String? = ""
            var courseId: Int = 0
            var desc: String? = ""
            var envelopePic: String? = ""
            var id: Int = 0
            var link: String? = ""
            var niceDate: String? = ""
            var origin: String? = ""
            var originId: Int = 0
            var publishTime: Long = 0
            var title: String? = ""
            var userId: Int = 0
            var visible: Int = 0
            var zan: Int = 0
        }
    }
}