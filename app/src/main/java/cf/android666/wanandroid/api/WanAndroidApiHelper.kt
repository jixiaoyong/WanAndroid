package cf.android666.wanandroid.api

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by jixiaoyong on 2018/3/2.
 */
object WanAndroidApiHelper {


    private var service = Retrofit.Builder()
            .baseUrl(ApiUrl.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WanAndroidService::class.java)


    fun getInstance(): WanAndroidService {

        return service
    }


}