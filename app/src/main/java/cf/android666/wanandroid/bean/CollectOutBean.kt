package cf.android666.wanandroid.bean


/**
 * Created by jixiaoyong on 2018/3/9.
 * email:jixiaoyong1995@gmail.com
 */
class CollectOutBean {


    /**
     * data : {"author":"cds","chapterId":0,"chapterName":"","courseId":13,"desc":"","envelopePic":"","id":4547,"link":"cddddddddddddd","niceDate":"刚刚","origin":"","originId":-1,"publishTime":1520597020038,"title":"dee","userId":2917,"visible":0,"zan":0}
     * errorCode : 0
     * errorMsg :
     */

    var data: DataBean = DataBean()
    var errorCode: Int = 0
    var errorMsg: String = ""


    class DataBean {
        /**
         * author : cds
         * chapterId : 0
         * chapterName :
         * courseId : 13
         * desc :
         * envelopePic :
         * id : 4547
         * link : cddddddddddddd
         * niceDate : 刚刚
         * origin :
         * originId : -1
         * publishTime : 1520597020038
         * title : dee
         * userId : 2917
         * visible : 0
         * zan : 0
         */

        var author: String = ""
        var chapterId: Int = 0
        var chapterName: String = ""
        var courseId: Int = 0
        var desc: String = ""
        var envelopePic: String = ""
        var id: Int = 0
        var link: String = ""
        var niceDate: String = ""
        var origin: String = ""
        var originId: Int = 0
        var publishTime: Long = 0
        var title: String = ""
        var userId: Int = 0
        var visible: Int = 0
        var zan: Int = 0
    }
}