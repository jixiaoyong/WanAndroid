package cf.android666.wanandroid.api

/**
 * Created by jixiaoyong on 2018/2/25.
 */

class ApiUrl{

    companion object {

        val baseUrl = "http://wanandroid.com/"

        //首页文章列表
        val atricleUrl = "http://www.wanandroid.com/article/list/0/json"

        //导航页
        val naviWebUrl = "http://wanandroid.com/navi"

        //导航页数据列表
        val naviUrl = "http://www.wanandroid.com/navi/json"

        //首页banner列表
        val bannerUrl = "http://www.wanandroid.com/banner/json"

        //常用网页列表
        val friendsUrl = "http://www.wanandroid.com/friend/json"

        //搜索热词列表
        val hotKeyUrl = "http://www.wanandroid.com/hotkey/json"

        //知识体系列表
        val treeUrl = "http://www.wanandroid.com/tree/json"

        //知识体系分类下的文章
        //        方法：GET
        //        参数：
        //        cid 分类的id，上述二级目录的id
        //        页码：拼接在链接上，从0开始。
        //        http://www.wanandroid.com/article/list/0?cid=60
        val treeArtivleUrl = "http://www.wanandroid.com/article/list/"

        //项目分类列表
        val projectsUrl = "http://www.wanandroid.com/project/tree/json"

        //项目列表的文章
        //        方法：GET
        //        参数：
        //        cid 分类的id，上面项目分类接口
        //        页码：拼接在链接中，从1开始。
        //http://www.wanandroid.com/project/list/1/json?cid=294
        val projectsItemUrl = "http://www.wanandroid.com/project/list/"

        //登录
        //        方法：POST
        //        参数：
        //        username，password
        val loginUrl = "http://www.wanandroid.com/user/login"

        //注册
        //方法：POST
        //参数
        //username,password,repassword
        val registerUrl = "http://www.wanandroid.com/user/register"

        //收藏列表
        //从0页开始
        val collectUrl = "http://www.wanandroid.com/lg/collect/list/0/json"

        //收藏站内文章
        //POST
        //输入文章id
        //http://www.wanandroid.com/lg/collect/1165/json
        val collectInSiteUrl = "http://www.wanandroid.com/lg/collect/"

        //收藏站外文章
        //方法：POST
        //参数：
        //title，author，link
        //http://www.wanandroid.com/lg/collect/add/json
        val collectOutSiteUrl = "http://www.wanandroid.com/lg/collect/"

        //取消收藏
        //1.输入文章列表中的文章id
        // http://www.wanandroid.com/lg/uncollect_originId/2333/json
        val uncollectByOriginIdUrl = "http://www.wanandroid.com/lg/uncollect_originId/"
        //2.输入收藏列表中的文章id
        //http://www.wanandroid.com/lg/uncollect/2805/json
        val uncollectByCollectIdUrl = "http://www.wanandroid.com/lg/uncollect/"

        //收藏网址列表
        val collectSiteUrl = "http://www.wanandroid.com/lg/collect/usertools/json"

        //更新收藏网址
        // 方法：POST
        //参数：
        //name,link
        val updateCollectSiteUrl = "http://www.wanandroid.com/lg/collect/addtool/json"

        //删除收藏的网址
        // 方法：POST
        //参数：
        //id
        val deletcCollectSiteUrl = "http://www.wanandroid.com/lg/collect/deletetool/json"

        //搜索
        //POST
        //参数：
        //页码：拼接在链接上，从0开始。
        //k ： 搜索关键词
        //注意：支持多个关键词，用空格隔开
        //http://www.wanandroid.com/article/query/0/json
        val searchUrl = "http://www.wanandroid.com/article/query/"


    }
}