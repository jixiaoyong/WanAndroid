package io.github.jixiaoyong.wanandroid.api

import io.github.jixiaoyong.wanandroid.api.bean.*
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by jixiaoyong on 2018/3/2.
 * 玩Android 开放API
 * https://wanandroid.com/blog/show/2
 */
interface WanAndroidService {

    /**
     * 首页文章列表
     * 方法：GET
     * 参数：页码，拼接在连接中，从0开始。
     */
    @GET("article/list/{page}/json")
    suspend fun getArticles(@Path("page") page: Int): RemoteDataBean<DataPageOf<DataIndexPostParam>>

    /**
     * 首页banner
     * 方法：GET
     * 参数：无
     */
    @GET("banner/json")
    fun getBanner(): Call<RemoteDataBean<List<DataBannerParam>>>

    /**
     * 常用网站
     * 方法：GET
     * 参数：无
     */
    @GET("friend/json")
    fun getFrinedLikns(): RemoteDataBean<List<DataFriendLinkParam>>


    /**
     * 搜索热词
     * 即目前搜索最多的关键词
     */
    @GET("hotkey/json")
    fun getHotKey(): RemoteDataBean<List<DataHotKeyParam>>

    /**
     * 置顶文章
     */
    @GET("article/top/json")
    fun getTopPosts(): RemoteDataBean<List<DataIndexPostParam>>


    /**
     * 体系数据
     * 方法：GET
     * 参数：无
     */
    @GET("tree/json")
    suspend fun getTree(): RemoteDataBean<List<DataSystemParam<DataSystemParam<Any>>>>

    /**
     * 知识体系下的文章
    方法：GET
    https://www.wanandroid.com/article/list/0/json?cid=60
    参数：
    cid 分类的id，上述二级目录的id
    页码：拼接在链接上，从0开始。
     */
    @GET("article/list/{page}/json")
    suspend fun getSystemPost(@Path("page") page: Int, @Query("cid") cid: Int)
            : RemoteDataBean<DataPageOf<DataIndexPostParam>>

    /**
     * 按照作者昵称搜索文章
    方法：GET
    https://wanandroid.com/article/list/0/json?author=鸿洋
    参数：
    页码：拼接在链接上，从0开始。
    author：作者昵称，不支持模糊匹配。
     */
    @GET("article/list/{page}/json")
    suspend fun getAuthorPost(@Path("page") page: Int, @Query("author") author: String)
            : RemoteDataBean<List<DataIndexPostParam>>

    /**
     * 导航数据
     * 方法：GET
     * 参数：无
     */
    @GET("navi/json")
    fun getNavi(): RemoteDataBean<List<DataNaviParam>>

    /**
     * 项目分类
     * 方法：GET
     * 参数：无
     */
    @GET("project/tree/json")
    suspend fun getProjectList(): RemoteDataBean<List<DataProjectParam>>


    /**
     * 项目列表数据
     * 方法：GET
     * 某一个分类下项目列表数据，分页展示
    参数：
    cid 分类的id，上面项目分类接口
    页码：拼接在链接中，从1开始。
     */
    //下面Url中有?，所以不能用@Path，要用@Query
    //http://www.wanandroid.com/project/list/1/json?cid=294
    @GET("project/list/{page}/json")
    suspend fun getProjectItems(@Path("page") page: Int, @Query("cid") cid: Int)
            : RemoteDataBean<DataPageOf<DataIndexPostParam>>


    /**
     * 登录
     * 方法：POST
    参数：
    username，password
     */
    @POST("user/login")
    @FormUrlEncoded
    suspend fun login(@Field("username") userName: String,
                      @Field("password") userPwd: String): CookieBean


    /**
     * 注册
     * 方法：POST
    参数
    username,password,repassword
     */
    @POST("user/register")
    @FormUrlEncoded //for @Field parameters can only be used with form encoding.
    suspend fun register(@Field("username") userName: String,
                         @Field("password") userPwd: String,
                         @Field("repassword") userRePwd: String): CookieBean

    /**
     * 退出
     */
    @GET("user/logout/json")
    suspend fun logout(): RemoteDataBean<Any>

    /**
     * 收藏文章列表
     * 方法：GET
    参数： 页码：拼接在链接中，从0开始。
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getCollect(@Path("page") page: Int): RemoteDataBean<DataPageOf<DataIndexPostParam>>

    /**
     * 收藏站内文章
     * 方法：POST
    参数： 文章id，拼接在链接中。
     */
    @POST("lg/collect/{id}/json")
    fun collectPostById(@Path("id") id: Int): Call<RemoteDataBean<Any>>

    /**
     * 收藏站外文章
     * 方法：POST
    参数：
    title，author，link
     */
    @POST("lg/collect/add/json")
    @FormUrlEncoded
    fun collectPostByInfo(@Field("title") title: String,
                          @Field("author") author: String,
                          @Field("link") link: String): RemoteDataBean<Any>

    /**
     * 取消收藏 -文章列表
     * 方法：POST
    参数：
    id:拼接在链接上(id传入的是列表中文章的id)
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun uncollectByOriginId(@Path("id") id: Int): RemoteDataBean<Any>

    /**
     * 我的收藏页面（该页面包含自己录入的内容）
     * 方法：POST
    参数：
    id:拼接在链接上
    originId:列表页下发，无则为-1
    originId 代表的是你收藏之前的那篇文章本身的id； 但是收藏支持主动添加，这种情况下，没有originId则为-1
     */
    //originId = -1
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    fun uncollectById(@Path("id") id: Int, @Field("originId") originId: Int = -1): Call<RemoteDataBean<Any>>

    /**
     * 收藏网站列表
     */
    @GET("lg/collect/usertools/json")
    fun getCollectWebsite(): RemoteDataBean<List<DataWebsiteParam>>

    /**
     * 收藏网址
     * 方法：POST
    参数：
    name,link
     */
    @POST("lg/collect/addtool/json")
    @FormUrlEncoded
    fun collectWebsiteByInfo(@Field("name") name: String,
                             @Field("link") link: String): RemoteDataBean<Any>

    /**
     * 编辑收藏网站
     * 方法：POST
    参数：
    id,name,link
     */
    @POST("lg/collect/updatetool/json")
    @FormUrlEncoded
    fun updateWebsite(@Field("id") id: Int,
                      @Field("name") name: String,
                      @Field("link") link: String): RemoteDataBean<Any>

    /**
     * 删除收藏网站
     * 方法：POST
    参数：
    id,name,link
     */
    @POST("lg/collect/deletetool/json")
    @FormUrlEncoded
    fun deleteWebsite(@Field("id") id: Int): RemoteDataBean<Any>


    /**
     * 搜索
     * 方法：POST
    参数：
    页码：拼接在链接上，从0开始。
    k ： 搜索关键词
    注意：支持多个关键词，用空格隔开
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    fun search(@Path("page") page: Int, @Field("k") key: String)
            : RemoteDataBean<DataPageOf<DataIndexPostParam>>


    /**
     * 获取个人积分
     * 需要登录后访问
     */
    @GET("lg/coin/userinfo/json")
    suspend fun getCoinInfo(): RemoteDataBean<DataCoinParam>

    /**
     * 获取个人积分获取列表
     * 需要登录后访问
     */
    @GET("lg/coin/list/1/json")
    fun getCoinHistory(): RemoteDataBean<DataCoinParam>

    /**
     * 积分排行榜接口
     */
    @GET("coin/rank/1/json")
    fun getCoinRank(): RemoteDataBean<DataPageOf<DataCoinHistoryParam>>

    /**
     * 广场列表数据
     * GET请求
    页码拼接在url上从0开始
    可能出现返回列表数据<每页数据，因为有自见的文章被过滤掉了。
     */
    @GET("user_article/list/{page}/json")
    suspend fun getPulicPlacePosts(@Path("page") page: Int): RemoteDataBean<DataPageOf<DataIndexPostParam>>

    /**
     * 分享人对应列表数据
     * GET请求
    参数：
    用户id: 拼接在url上
    页码拼接在url上从1开始
    这个展示的文章数据都是审核通过的，一般是点击分享人然后展示的列表。
     */
    @GET("user/{id}/share_articles/{page}/json")
    fun getPulicPlacePostsOfUser(@Path("id") id: Int, @Path("page") page: Int)
            : RemoteDataBean<DataPublicPostOfUserParam>

    /**
     * 自己的分享的文章列表
     * GET请求
    参数：
    页码，从1开始
     */
    @GET("user/lg/private_articles/{page}/json")
    fun getPulicPlacePostsOfUser(@Path("page") page: Int)
            : RemoteDataBean<DataPublicPostOfUserParam>

    /**
     * 删除自己分享的文章
     * 方法：POST
    参数：
    文章id，拼接在链接上
     */
    @POST("lg/user_article/delete/{id}/json")
    fun deletePublicPostOfMe(@Path("id") id: Int): RemoteDataBean<Any>

    /**
     * 分享文章
     * 方法：POST
    参数：
    title:
    link
     */
    @POST("lg/user_article/add/json")
    @FormUrlEncoded
    fun deletePublicPostOfMe(@Field("title") title: String, @Field("link") link: String): RemoteDataBean<Any>


    /**
     * 获取公众号列表
     * 方法：GET
     * 参数：无
     */
    @GET("wxarticle/chapters/json")
    suspend fun getWechatList(): RemoteDataBean<List<DataSystemParam<Any>>>

    /**
     * 查看某个公众号历史数据
    https://wanandroid.com/wxarticle/list/408/1/json
    方法：GET
    参数：
    公众号 ID：拼接在 url 中，eg:405
    公众号页码：拼接在url 中，eg:1
     */
    @GET("wxarticle/list/{wechatId}/{page}/json")
    suspend fun getWechatPost(@Path("wechatId") wechatId: Int, @Path("page") page: Int)
            : RemoteDataBean<DataPageOf<DataIndexPostParam>>

}