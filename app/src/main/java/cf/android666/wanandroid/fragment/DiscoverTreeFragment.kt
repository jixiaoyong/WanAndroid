package cf.android666.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.adapter.DiscoverTreeAdapter
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.DiscoverTreeBean
import cf.android666.wanandroid.api.cookie.CookieTools
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_discover_tree.view.*


/**
 * Created by jixiaoyong on 2018/2/25.
 */
class DiscoverTreeFragment : BaseFragment() {

    override fun onCreateViewState(savedInstanceState: Bundle?) {
        mView!!.expandable_layout.setAdapter(DiscoverTreeAdapter(context, mData))
    }

    override fun lazyLoadData() {

        CookieTools.getCookieService()!!
                .getTree()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    mData.clear()

                    mData.addAll(it.data)

                    mView!!.expandable_layout.setAdapter(DiscoverTreeAdapter(context, mData))
                }
    }

    override var layoutId = R.layout.fragment_discover_tree

    private var mData: ArrayList<DiscoverTreeBean.DataBean> = arrayListOf()

}