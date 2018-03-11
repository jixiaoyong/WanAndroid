package cf.android666.wanandroid.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cf.android666.wanandroid.R
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.adapter.PostArticleAdapter
import cf.android666.wanandroid.api.WanAndroidApiHelper
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.BaseArticlesBean
import cf.android666.wanandroid.cookie.CookieTools
import cf.android666.wanandroid.utils.SuperUtil
import cf.android666.wanandroid.utils.TempTools
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_index_post.view.*
import com.zhouwei.mzbanner.MZBannerView

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class IndexPostFragment : BaseFragment() {


    private var mData : ArrayList<BaseArticlesBean> = arrayListOf()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_index_post, container, false)

        //banner
        var mMZBanner = view.banner

        view.recycler_view.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)

        view.recycler_view.adapter = PostArticleAdapter(context, mData, {

            SuperUtil.startActivity(context, ContentActivity::class.java, it)

        }, { position, isSelected ->

            var postId = mData[position].id

                if (isSelected) {

                    collectPost(postId)

                } else {

                    unCollectPost(postId)
                }



        })

        downloadData()
        downloadBanner(mMZBanner)

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

                    view!!.recycler_view.adapter.notifyDataSetChanged()

                }
    }

    private fun downloadBanner(mMZBanner: MZBannerView<*>) {

        val observable2 = WanAndroidApiHelper.getInstance().getBanner()

        observable2.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    TempTools.setBanner(it.data, mMZBanner)

                }
    }

}

