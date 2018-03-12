package cf.android666.wanandroid.api

import cf.android666.wanandroid.bean.UpdateInfoBean
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by jixiaoyong on 2018/3/11.
 * email:jixiaoyong1995@gmail.com
 */
interface UpdateService{

    //apk url
    //https://raw.githubusercontent.com/jixiaoyong/Notes-Files/master/download/apk/WanAndroid.apk

    companion object {

        const val BASE_URL = "https://raw.githubusercontent.com/"
    }


    //获取项目分类
    @GET("project/tree/json")
    fun getUpdateInfo() : Observable<UpdateInfoBean>
}