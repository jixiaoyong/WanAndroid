package cf.android666.wanandroid.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cf.android666.mylibrary.view.SwitchStateLayout
import cf.android666.wanandroid.R
import cf.android666.wanandroid.`interface`.RefreshUiInterface
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.adapter.PostArticleAdapter
import cf.android666.wanandroid.api.cookie.CookieTools
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.BaseArticlesBean
import cf.android666.wanandroid.utils.*
import com.zhouwei.mzbanner.MZBannerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_index_post.*
import kotlinx.android.synthetic.main.fragment_index_post.view.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class IndexPostFragment : BaseFragment(), RefreshUiInterface {

    override fun refreshUi(event: EventInterface) {

        when (event) {
            is EventFactory.LoginState -> {
                downloadData()
            }
        }

        downloadBanner(mView!!.banner)

    }

    override var layoutId = R.layout.fragment_index_post

    private var mData: ArrayList<BaseArticlesBean> = arrayListOf()

    private var currentPage = 0

    private var pageCount = 0

    private var childCount = 0

    var hasLoadData = false

    override fun onCreateViewState(savedInstanceState: Bundle?) {

        mView!!.switch_state.showView(SwitchStateLayout.VIEW_LOADING)

        mView!!.recycler_view.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)

        mView!!.recycler_view.adapter = PostArticleAdapter(mData, false, {

            SuperUtil.startActivity(requireContext(), ContentActivity::class.java, it)

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

        val recyclerView = mView!!.recycler_view
        mView!!.nested_scrollview.setOnScrollChangeListener { v: NestedScrollView?,
                                                              scrollX: Int, scrollY: Int,
                                                              oldScrollX: Int, oldScrollY: Int ->

            if ((scrollY - oldScrollY) > 0
                    && (scrollY + v!!.measuredHeight) == mView!!.switch_state.measuredHeight
                    && recyclerView.scrollState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE) {

                Toast.makeText(context,"更新数据中...",Toast.LENGTH_SHORT).show()
                downloadData(++currentPage)

            }
        }

        mView!!.swipe_refresh.setOnRefreshListener {
            //            lazyLoadData()
            downloadData()
            downloadBanner(mView!!.banner)
        }

    }

    override fun lazyLoadData() {

        if (!hasLoadData) {

            downloadData()
            downloadBanner(mView!!.banner)
            hasLoadData = true
        }
    }

    private fun unCollectPost(postId: Int) {

        CookieTools.getCookieService()!!
                .uncollectByOriginId(postId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    if (it.errorCode < 0) {
                        Toast.makeText(context, it.errorMsg, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "取消收藏成功", Toast.LENGTH_SHORT).show()

                        EventBus.getDefault().post(EventFactory.CollectState(-postId))

                    }

                }, {

                    mView!!.switch_state.showView(SwitchStateLayout.VIEW_ERROR)

                }, {
                    mView!!.switch_state.showContentView()

                }

                )
    }

    private fun collectPost(postId: Int) {

        CookieTools.getCookieService()!!
                .collectPostById(postId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    if (it.errorCode < 0) {
                        Toast.makeText(context, it.errorMsg, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show()

                        EventBus.getDefault().post(EventFactory.CollectState(postId))
                    }
                    mView!!.switch_state.showContentView()
                }, {

                    mView!!.switch_state.showView(SwitchStateLayout.VIEW_ERROR)

                }, {
                    mView!!.switch_state.showContentView()

                })

    }

    private fun downloadData() {

        downloadData(0)

    }

    private fun downloadData(page: Int) {

        mView!!.switch_state.showView(SwitchStateLayout.VIEW_LOADING)

        val observable = CookieTools.getCookieService()!!.getArticles(page)

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    when (page) {

                        0 -> {
                            mData.clear();mData.addAll(it.data.datas)
                        }

                        else -> mData.addAll(it.data.datas)
                    }

                    pageCount = it.data.pageCount

                    currentPage = it.data.curPage

                    if (view != null) {

                        view!!.recycler_view.adapter?.notifyDataSetChanged()
                        childCount = recycler_view.childCount
                    }

                    view?.swipe_refresh?.isRefreshing = false
                }, {

                    mView!!.switch_state.showView(SwitchStateLayout.VIEW_ERROR)

                }, {
                    mView!!.switch_state.showContentView()

                })
    }

    private fun downloadBanner(mMZBanner: MZBannerView<*>) {

        CookieTools.getCookieService()!!
                .getBanner()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    TempTools.setBanner(it.data, mMZBanner)

                }, {

                    mView!!.switch_state.showView(SwitchStateLayout.VIEW_ERROR)

                }, {
                    mView!!.switch_state.showContentView()

                })
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

