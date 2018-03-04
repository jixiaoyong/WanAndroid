package cf.android666.wanandroid.api

import cf.android666.wanandroid.bean.DiscoverProjectItemBean
import cf.android666.wanandroid.bean.DiscoverProjectTreeBean
import cf.android666.wanandroid.bean.DiscoverTreeBean
import cf.android666.wanandroid.bean.IndexArticleBean
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by jixiaoyong on 2018/3/2.
 */

interface WanAndroidService{

    companion object {
        val BASE_URL = "http://www.wanandroid.com/"
    }

    @GET("tree/json")
    fun getTree() :Observable<DiscoverTreeBean>

    @GET("article/list/{page}/json")
    fun getArticles(@Path("page") page: Int): Observable<IndexArticleBean>

    //下面Url中有?，所以不能用@Path，要用@Query
    //http://www.wanandroid.com/project/list/1/json?cid=294
    @GET("project/list/{page}/json")
    fun getProjectItems(@Path("page") page: Int, @Query("cid") cid: Int): Observable<DiscoverProjectItemBean>

    //获取项目分类
    @GET("project/tree/json")
    fun getProjectTree() :Observable<DiscoverProjectTreeBean>

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<DiscoverTreeBean>>
}