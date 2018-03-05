package cf.android666.wanandroid.fragment

import android.content.Context
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
import cf.android666.wanandroid.utils.SuperUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_index_favorite.*
import kotlinx.android.synthetic.main.fragment_index_favorite.view.*

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class IndexFavoriteFragment() : BaseFragment() {

    private var mData = IndexCollectBean.DataBean().datas


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_index_favorite, container, false)

        view.noting_img.visibility = View.VISIBLE

        view.recycler_view.visibility = View.GONE

        view.recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        view.recycler_view.adapter = IndexFavoriteAdapter(context, mData, {

            SuperUtil.startActivity(context, ContentActivity::class.java, it)

        }, {

            it.isSelected = !it.isSelected

        })

        CookieTools.getCookieService()!!
                .getCollect(0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    view.noting_img.visibility = View.GONE

                    view.recycler_view.visibility = View.VISIBLE

                    mData.clear()

                    mData.addAll(it.data.datas)

                    view.recycler_view.adapter.notifyDataSetChanged()

                }

        return view
    }



}