package cf.android666.wanandroid.api

import cf.android666.wanandroid.bean.DiscoverTree
import io.reactivex.Observable
import io.reactivex.Observer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by jixiaoyong on 2018/3/2.
 */

interface WanAndroidService{

    companion object {
        val BASE_URL = "http://www.wanandroid.com/"
    }

    @GET("project/tree/json")
    fun getTree() :Observable<DiscoverTree>

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<DiscoverTree>>
}