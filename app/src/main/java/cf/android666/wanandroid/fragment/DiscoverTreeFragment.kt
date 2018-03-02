package cf.android666.wanandroid.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.adapter.MyRecyclerAdapter
import cf.android666.wanandroid.api.ApiUrl
import cf.android666.wanandroid.api.WanAndroidApiHelper
import cf.android666.wanandroid.api.WanAndroidService
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.DiscoverTree
import cf.android666.wanandroid.view.ContactsRecyclerview
import com.google.gson.GsonBuilder
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.RxThreadFactory
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_discover_tree.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class DiscoverTreeFragment() : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_discover_tree, container, false)

        val names = arrayOf("你nihao你", "爱好Joan", "的Niki", "Betty", "Linda", "Whitney", "Lily", "Fred", "Gary", "William", "Charles", "Michael", "Karl", "arbara", "Elizabeth", "Helen", "Katharine", "Lee", "Ann", "Diana", "Fiona")

        val data: ArrayList<String> = arrayListOf()

        names.indices.mapTo(data) { names[it] }

        view.recycler_view.adapter = MyRecyclerAdapter(context, data)

        view.recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        view.recycler_view.addItemDecoration(ContactsRecyclerview.Decoration(data))


        var service: WanAndroidService = WanAndroidApiHelper.getInstance()


        var observable :Observable<DiscoverTree> = service.getTree()

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result ->
                    Logger.wtf("hh")
                    Logger.d(result.data[0].name)
                },{
                    error ->
                    error.printStackTrace()
                })







        return view
    }
}