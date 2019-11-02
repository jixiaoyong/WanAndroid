package cf.android666.wanandroid.fragment

import android.os.Bundle
import cf.android666.mylibrary.view.SwitchStateLayout
import cf.android666.wanandroid.R
import cf.android666.wanandroid.adapter.DiscoverTreeAdapter
import cf.android666.wanandroid.api.cookie.CookieTools
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.DiscoverTreeBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_discover_tree.view.*


/**
 * Created by jixiaoyong on 2018/2/25.
 */
class DiscoverTreeFragment : BaseFragment() {

    override fun onCreateViewState(savedInstanceState: Bundle?) {
        mView!!.switch_state.showView(SwitchStateLayout.VIEW_EMPTY)
        mView!!.expandable_layout.setAdapter(DiscoverTreeAdapter(requireContext(), mData))
    }

    override fun lazyLoadData() {
        mView!!.switch_state.showView(SwitchStateLayout.VIEW_LOADING)

        CookieTools.getCookieService()!!
                .getTree()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mData.clear()
                    mData.addAll(it.data)
                    mView!!.expandable_layout.setAdapter(DiscoverTreeAdapter(requireContext(), mData))
                }, {
                    mView!!.switch_state.showView(SwitchStateLayout.VIEW_ERROR)
                }, {
                    mView!!.switch_state.showContentView()
                })
    }

    override var layoutId = R.layout.fragment_discover_tree

    private var mData: ArrayList<DiscoverTreeBean.DataBean> = arrayListOf()

}