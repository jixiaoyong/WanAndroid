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

    // update info url
    // http://jixiaoyong.github.io/download/data/wanandroid/updateInfo.json

    companion object {

        const val BASE_URL = "http://jixiaoyong.github.io/"
    }


    //获取项目分类
    @GET("download/data/wanandroid/updateInfo.json")
    fun getUpdateInfo() : Observable<UpdateInfoBean>
}