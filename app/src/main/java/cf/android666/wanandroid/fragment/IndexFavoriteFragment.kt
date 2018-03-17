package cf.android666.wanandroid.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cf.android666.wanandroid.R
import cf.android666.wanandroid.`interface`.RefreshUiInterface
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.adapter.PostArticleAdapter
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.BaseArticlesBean
import cf.android666.wanandroid.api.cookie.CookieTools
import cf.android666.wanandroid.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_index_favorite.view.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class IndexFavoriteFragment : BaseFragment() , RefreshUiInterface {

    override fun refreshUi(event: EventInterface) {

        view?.let { loadData(it) }

    }

    override var layoutId = R.layout.fragment_index_favorite

    private var mData: ArrayList<BaseArticlesBean> = arrayListOf()


    private var currentPage = 0


    private var pageCount = 0

    override fun onCreateViewState(savedInstanceState: Bundle?) {

        mView!!.swipe_refresh.setOnRefreshListener {
            mView!!.swipe_refresh.isRefreshing = true

            loadData(mView!!)
        }

        var isLogin = SharePreference.getV<Boolean>(SharePreference.IS_LOGIN, false)

        if (isLogin) {

            mView!!.recycler_view.layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL, false)

            mView!!.recycler_view.adapter = PostArticleAdapter(mData,
                    true,
                    {

                        SuperUtil.startActivity(context, ContentActivity::class.java, it)

                    }, { it, position ->

                it.isSelected = false

                unCollectPost(mData[position].id)

                mData.removeAt(position)

                mView!!.recycler_view.adapter.notifyDataSetChanged()

            })

            mView!!.noting_img.visibility = View.GONE

            mView!!.recycler_view.visibility = View.VISIBLE

        } else {

            mView!!.noting_img.visibility = View.VISIBLE

            mView!!.recycler_view.visibility = View.GONE

            Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show()
        }


        mView!!.recycler_view.setOnFootListener {

            if (currentPage < pageCount){
                loadData(mView!!,++currentPage)
            }
        }

    }

    override fun lazyLoadData() {

        loadData(mView!!)

    }

    private fun unCollectPost(id: Int) {

        CookieTools.getCookieService()!!
                .uncollectById(id, -1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe {
                    if (it.errorCode < 0) {
                        Toast.makeText(context, it.errorMsg, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "取消收藏成功", Toast.LENGTH_SHORT).show()
                        EventBus.getDefault().post(EventFactory.CollectState(-id))
                    }
                }
    }

    private fun loadData(view: View?){
        loadData(view,0)
    }

    private fun loadData(view: View?, page:Int) {

        CookieTools.getCookieService()!!
                .getCollect(0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    view?.swipe_refresh?.isRefreshing = false

                    if (it.errorCode < 0) {

                        Toast.makeText(context, it.errorMsg, Toast.LENGTH_SHORT).show()

                        SharePreference.saveKV(SharePreference.IS_LOGIN, false)


                    } else {

                        when (page) {

                            0 -> {
                                mData.clear();mData.addAll(it.data.datas)
                            }

                            else -> mData.addAll(it.data.datas)
                        }


                        pageCount = it.data.pageCount

                        currentPage = it.data.curPage

                        view?.recycler_view?.adapter?.notifyDataSetChanged()

                        SharePreference.saveKV(SharePreference.FAVORITE_COUNT, it.data.total.toString())
                    }


                }
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)

    }

}