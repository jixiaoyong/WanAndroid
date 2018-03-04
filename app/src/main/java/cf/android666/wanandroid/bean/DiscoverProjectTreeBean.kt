package cf.android666.wanandroid.bean

/**
 * Created by jixiaoyong on 2018/2/28.
 */

class DiscoverProjectTreeBean{


    /**
     * data : [{"children":[],"courseId":13,"id":294,"name":"完整项目","order":145000,"parentChapterId":293,"visible":0},{"children":[],"courseId":13,"id":309,"name":"表格控件","order":145001,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":310,"name":"下拉刷新","order":145002,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":312,"name":"富文本编辑器","order":145003,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":314,"name":"RV列表动效","order":145004,"parentChapterId":293,"visible":1},{"children":[],"courseId":13,"id":316,"name":"系统源码分析","order":145005,"parentChapterId":293,"visible":1}]
     * errorCode : 0
     * errorMsg :
     */

     var errorCode: Int = 0

     var errorMsg: String = ""

     var data :List<DataBean> = arrayListOf()


    class DataBean {
        /**
         * children : []
         * courseId : 13
         * id : 294
         * name : 完整项目
         * order : 145000
         * parentChapterId : 293
         * visible : 0
         */

        var courseId: Int = 0
        var id: Int = 0
        var name: String? = ""
        var order: Int = 0
        var parentChapterId: Int = 0
        var visible: Int = 0
        var children: List<*>? = null
    }

}