package cf.android666.wanandroid.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cf.android666.wanandroid.R
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.adapter.IndexFavoriteAdapter
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.IndexCollectBean
import cf.android666.wanandroid.cookie.CookieTools
import cf.android666.wanandroid.utils.MessageEvent
import cf.android666.wanandroid.utils.SharePreference
import cf.android666.wanandroid.utils.SuperUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_index_favorite.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class IndexFavoriteFragment() : BaseFragment() {

//    private var mData = IndexCollectBean.DataBean().datas

    private var mData :ArrayList<IndexCollectBean.DataBean.DatasBean> = arrayListOf()

    var mView: View? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_index_favorite, container, false)

        mView = view

        view.swipe_refresh.setOnRefreshListener {
            view.swipe_refresh.isRefreshing = true

            loadData(view)
        }

        var isLogin = SharePreference.getV<Boolean>(SharePreference.IS_LOGIN,false)


        if (isLogin) {

            view.recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            view.recycler_view.adapter = IndexFavoriteAdapter(context, mData, {

                SuperUtil.startActivity(context, ContentActivity::class.java, it)

            }, {

                unCollectPost(mData[it].id)

                mData.removeAt(it)

                view.recycler_view.adapter.notifyDataSetChanged()

            })

            loadData(view)

            view.noting_img.visibility = View.GONE

            view.recycler_view.visibility = View.VISIBLE

        } else {

            view.noting_img.visibility = View.VISIBLE

            view.recycler_view.visibility = View.GONE

            Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun unCollectPost(id: Int) {

        CookieTools.getCookieService()!!
                .uncollectById(id,-1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe {
                    if (it.errorCode < 0) {
                        Toast.makeText(context, it.errorMsg, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "取消收藏成功", Toast.LENGTH_SHORT).show()
                        //todo 更新状态
                    }
                }
    }


    fun loadData(view: View) {

        CookieTools.getCookieService()!!
                .getCollect(0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    view.swipe_refresh.isRefreshing = false

                    if (it.errorCode < 0) {

                        Toast.makeText(context, it.errorMsg, Toast.LENGTH_SHORT).show()

                        SharePreference.saveKV(SharePreference.IS_LOGIN, false)


                    } else {

                        mData.clear()

                        mData.addAll(it.data.datas)

                        view.recycler_view.adapter.notifyDataSetChanged()

                        SharePreference.saveKV(SharePreference.FAVORITE_COUNT,it.data.total.toString())
                    }


                }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun update(event: MessageEvent) {

        if (view != null) {
            loadData(view!!)
        }

    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }
}