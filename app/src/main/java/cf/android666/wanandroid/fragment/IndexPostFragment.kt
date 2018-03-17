package cf.android666.wanandroid.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cf.android666.mylibrary.view.SwitchStateLayout
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
import kotlinx.android.synthetic.main.fragment_index_post.view.*
import com.zhouwei.mzbanner.MZBannerView
import kotlinx.android.synthetic.main.activity_search.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class IndexPostFragment : BaseFragment(), RefreshUiInterface {

    override fun refreshUi(event: EventInterface) {

        downloadData()

    }

    override var layoutId = R.layout.fragment_index_post

    private var mData: ArrayList<BaseArticlesBean> = arrayListOf()

    private var currentPage = 0

    private var pageCount = 0

    private var childCount = 0

    override fun onCreateViewState(savedInstanceState: Bundle?) {

        mView!!.switch_state.showView(SwitchStateLayout.VIEW_LOADING)

        mView!!.recycler_view.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)

        mView!!.recycler_view.adapter = PostArticleAdapter(mData, false, {

            SuperUtil.startActivity(context, ContentActivity::class.java, it)

        }, { view, position ->

            var isLogin = SharePreference.getV<Boolean>(SharePreference.IS_LOGIN, false)

            if (isLogin) {

                view.isSelected = !view.isSelected

            }

            var postId = mData[position].id

            if (view.isSelected) {

                collectPost(postId)

            } else {

                unCollectPost(postId)
            }

        })

        //onTouchEvent事件被NestedScrollView拦截，故而不起作用
        mView!!.recycler_view.setOnFootListener {

            if (currentPage < pageCount) {

                downloadData(++currentPage)

            }
        }

        mView!!.recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                var lastPosition = (recyclerView!!.layoutManager as LinearLayoutManager)
                        .findLastVisibleItemPosition()

                if (lastPosition > childCount -2 && currentPage < pageCount) {

                    downloadData(++currentPage)

                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        mView!!.swipe_refresh.setOnRefreshListener {
            lazyLoadData()
        }

    }

    override fun lazyLoadData() {
        downloadData()
        downloadBanner( mView!!.banner)
    }

    private fun unCollectPost(postId: Int) {

        CookieTools.getCookieService()!!
                .uncollectByOriginId(postId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe {
                    if (it.errorCode < 0) {
                        Toast.makeText(context, it.errorMsg, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "取消收藏成功", Toast.LENGTH_SHORT).show()

                        EventBus.getDefault().post(EventFactory.CollectState(-postId))

                    }
                }
    }

    private fun collectPost(postId: Int) {

        CookieTools.getCookieService()!!
                .collectPostById(postId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe {
                    if (it.errorCode < 0) {
                        Toast.makeText(context, it.errorMsg, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show()

                        EventBus.getDefault().post(EventFactory.CollectState(postId))

                    }
                }

    }

    private fun downloadData() {

        downloadData(0)

    }

    private fun downloadData(page: Int) {

        val observable = CookieTools.getCookieService()!!.getArticles(page)

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    when (page) {

                        0 -> {
                            mData.clear();mData.addAll(it.data.datas)
                        }

                        else -> mData.addAll(it.data.datas)
                    }

                    pageCount = it.data.pageCount

                    currentPage = it.data.curPage

                    if (view != null) {

                        view!!.recycler_view.adapter.notifyDataSetChanged()
                        childCount = recycler_view.childCount
                    }


                    view?.swipe_refresh?.isRefreshing = false
                }
    }

    private fun downloadBanner(mMZBanner: MZBannerView<*>) {

        CookieTools.getCookieService()!!
                .getBanner()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    TempTools.setBanner(it.data, mMZBanner)

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

