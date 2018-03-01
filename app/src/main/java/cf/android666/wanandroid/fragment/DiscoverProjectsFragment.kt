package cf.android666.wanandroid.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cf.android666.wanandroid.R
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.adapter.DiscoverProjectsAdapter
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.DiscoverProjectItemBean
import cf.android666.wanandroid.utils.DownloadUtil
import cf.android666.wanandroid.utils.SuperUtil
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_discover_projects.view.*
import java.util.ArrayList

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class DiscoverProjectsFragment : BaseFragment() {

    private var mData = DiscoverProjectItemBean()


    private var handler = @SuppressLint("HandlerLeak")
    object : android.os.Handler() {

        override fun handleMessage(msg: Message?) {
            if (msg!!.what == MSG_WHAT_PROJECTS) {

                view!!.swipe_refresh.isRefreshing = false

                if (msg.obj == null) {
                    return
                }

                mData = Gson().fromJson<DiscoverProjectItemBean>(msg.obj as String)

                if (mData.errorCode >= 0) {

                    view!!.recycler_view.adapter.notifyDataSetChanged()

                    Logger.d("start view!!.recycler_view.adapter.notifyDataSetChanged()")

                    Logger.d(mData.data.datas[0])

                } else {

                    Toast.makeText(context, mData.errorMsg, Toast.LENGTH_SHORT).show()

                }


            } else if (msg!!.what == MSG_WHAT_TREE) {

            }
        }
    }

    companion object {

        val MSG_WHAT_PROJECTS = 0

        val MSG_WHAT_TREE = 1

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_discover_projects, container, false)

        view.recycler_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

//        view.recycler_view.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        var mData  = ArrayList<DiscoverProjectItemBean.DataBean.DatasBean>()

        for (x in 0..20){

            var temp = DiscoverProjectItemBean.DataBean.DatasBean()
            temp.apkLink = "ddede" + x
            temp.author = "ddede" + x
            temp.title = "ddede" + x
            temp.desc = "ddede" + x
            temp.envelopePic = "ddede" + x

            mData.add(temp)
        }

//        view.recycler_view.adapter = DiscoverProjectsAdapter(context, mData.data.datas, {
        view.recycler_view.adapter = DiscoverProjectsAdapter(context, mData, {

            SuperUtil.startActivity(context, ContentActivity::class.java, it)

        }, {

        })

        val url = "http://www.wanandroid.com/project/list/1/json?cid=294"

        DownloadUtil.downloadJson(url,handler, MSG_WHAT_PROJECTS)

        view.swipe_refresh.setOnRefreshListener{

            DownloadUtil.downloadJson(url,handler, MSG_WHAT_PROJECTS)

        }

        return view
    }

    inline fun <reified T : Any> Gson.fromJson(json: String): T {

        return Gson().fromJson(json, T::class.java)

    }
}


