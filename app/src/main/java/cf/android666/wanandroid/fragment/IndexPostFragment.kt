package cf.android666.wanandroid.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cf.android666.wanandroid.R
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.adapter.PostArticleAdapter
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.BaseArticlesBean
import cf.android666.wanandroid.api.cookie.CookieTools
import cf.android666.wanandroid.utils.SharePreference
import cf.android666.wanandroid.utils.SuperUtil
import cf.android666.wanandroid.utils.TempTools
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_index_post.view.*
import com.zhouwei.mzbanner.MZBannerView
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class IndexPostFragment : BaseFragment() {

    private var mData: ArrayList<BaseArticlesBean> = arrayListOf()

    private var currentPage = 0

    private var pageCount = 0

    private var childCount = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_index_post, container, false)

        //banner
        var mMZBanner = view.banner

        view.recycler_view.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)

        view.recycler_view.adapter = PostArticleAdapter(mData, false, {

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
        view.recycler_view.setOnFootListener {

            if (currentPage < pageCount) {

                downloadData(++currentPage)

            }
        }

        view.recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                var lastPosition = (recyclerView!!.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (lastPosition > childCount -2 && currentPage < pageCount) {

                    downloadData(++currentPage)

                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        downloadData()
        downloadBanner(mMZBanner)

        view.swipe_refresh.setOnRefreshListener {
            downloadData()
            downloadBanner(mMZBanner)
        }

        return view
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
                        //todo 更新状态
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
                        //todo 更新状态
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

}

