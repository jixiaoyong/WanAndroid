package cf.android666.wanandroid.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import cf.android666.wanandroid.R
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.adapter.BannerViewHolder
import cf.android666.wanandroid.adapter.IndexPostArticleAdapter
import cf.android666.wanandroid.adapter.IndexPostBannerAdapter
import cf.android666.wanandroid.api.ApiUrl
import cf.android666.wanandroid.api.WanAndroidApiHelper
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.IndexArticleBean
import cf.android666.wanandroid.bean.IndexBannerBean
import cf.android666.wanandroid.utils.DownloadUtil
import cf.android666.wanandroid.utils.LogTools
import cf.android666.wanandroid.utils.SuperUtil
import cf.android666.wanandroid.utils.TempTools
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_index_post.view.*
import com.zhouwei.mzbanner.holder.MZHolderCreator
import com.zhouwei.mzbanner.MZBannerView
import com.zhouwei.mzbanner.holder.MZViewHolder





/**
 * Created by jixiaoyong on 2018/2/25.
 */
class IndexPostFragment : BaseFragment() {


    private var mData = IndexArticleBean.DataBean().datas

    private var mBannerData = IndexBannerBean().data

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_index_post, container, false)


        //banner
        var mMZBanner = view.banner

//        TempTools.setBanner(mBannerData,mMZBanner)

//        // 设置数据
//        mMZBanner.setPages(mBannerData,object : MZHolderCreator<BannerViewHolder>{
//
//            override fun createViewHolder(): BannerViewHolder {
//                return BannerViewHolder()
//            }
//        })


        view.recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        view.recycler_view.adapter = IndexPostArticleAdapter(context, mData, {

            SuperUtil.startActivity(context, ContentActivity::class.java, it)

        }, {

            it.isSelected = !it.isSelected

        })

        downloadData()
        downloadBanner(mMZBanner)

        return view
    }

    private fun downloadData() {

        downloadData(0)

    }

    private fun downloadData(page: Int) {

        val observable = WanAndroidApiHelper.getInstance().getArticles(page)

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    when(page){

                        0 -> {mData.clear();mData.addAll(it.data.datas)}

                        else -> mData.addAll(it.data.datas)
                    }

                    view!!.recycler_view.adapter.notifyDataSetChanged()

                }
    }

    private fun downloadBanner(mMZBanner :MZBannerView<*>){

        val observable2 = WanAndroidApiHelper.getInstance().getBanner()

        observable2.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    var ss = it

                    TempTools.setBanner(it.data,mMZBanner)

                }
    }

}

