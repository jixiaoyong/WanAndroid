package cf.android666.wanandroid.api

import android.text.Editable
import cf.android666.wanandroid.bean.*
import io.reactivex.Observable
import okhttp3.Cookie
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by jixiaoyong on 2018/3/2.
 */

interface WanAndroidService{

    companion object {
        const val BASE_URL = "http://www.wanandroid.com/"
    }

    @GET("tree/json")
    fun getTree() :Observable<DiscoverTreeBean>

    @GET("navi/json")
    fun getNavi() :Observable<DiscoverNaviBean>

    @GET("banner/json")
    fun getBanner() :Observable<IndexBannerBean>

    @GET("article/list/{page}/json")
    fun getArticles(@Path("page") page: Int): Observable<IndexArticleBean>

    //下面Url中有?，所以不能用@Path，要用@Query
    //http://www.wanandroid.com/project/list/1/json?cid=294
    @GET("project/list/{page}/json")
    fun getProjectItems(@Path("page") page: Int, @Query("cid") cid: Int): Observable<DiscoverProjectItemBean>

    //获取项目分类
    @GET("project/tree/json")
    fun getProjectTree() :Observable<DiscoverProjectTreeBean>


    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") userName: String,
              @Field("password") userPwd: String): Observable<CookieBean>


    @POST("user/register")
    @FormUrlEncoded //for @Field parameters can only be used with form encoding.
    fun register(@Field("username") userName: String,
                 @Field("password") userPwd: String,
                 @Field("repassword")userRePwd: String) : Observable<CookieBean>

    @GET("lg/collect/list/{page}/json")
    fun getCollect(@Path("page") page: Int):Observable<IndexArticleBean>

    @POST("lg/collect/{id}/json")
    fun collectPostById(@Path("id") id: Int):Observable<CollectBean>

    @POST("lg/collect/add/json")
    @FormUrlEncoded
    fun collectPostByInfo(@Field("title") title: String,
                          @Field("author") author: String,
                          @Field("link")link: String):Observable<CollectOutBean>

    @POST("lg/uncollect_originId/{id}/json")
    fun uncollectByOriginId(@Path("id") id: Int):Observable<CollectBean>

    //originId = -1
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    fun uncollectById(@Path("id") id: Int,@Field("originId")originId:Int):Observable<CollectBean>

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    fun search(@Path("page") page: Int, @Field("k") key: String):Observable<SearchBean>
}