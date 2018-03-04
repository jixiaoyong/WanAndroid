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
        val BASE_URL = "http://www.wanandroid.com/"
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
    fun login(@Field("username") userName: String,
              @Field("password") userPwd: String): Observable<Cookie>

    @POST("user/login")
    fun register(@Field("username") userName: String,
                 @Field("password") userPwd: String,
                 @Field("repassword")userRePwd: String) : Observable<Cookie>

}