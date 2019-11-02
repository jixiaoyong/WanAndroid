package cf.android666.wanandroid.fragment

import android.os.Bundle
import cf.android666.mylibrary.view.SwitchStateLayout
import cf.android666.wanandroid.R
import cf.android666.wanandroid.adapter.DiscoverNaviAdapter
import cf.android666.wanandroid.api.cookie.CookieTools
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.DiscoverNaviBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_discover_tree.view.*

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class DiscoverNaviFragment : BaseFragment() {

    override var layoutId = R.layout.fragment_discover_tree

    private var mData: ArrayList<DiscoverNaviBean.DataBean> = arrayListOf()

    override fun onCreateViewState(savedInstanceState: Bundle?) {

        mView!!.switch_state.showView(SwitchStateLayout.VIEW_EMPTY)


    }

    override fun lazyLoadData() {

        mView!!.switch_state.showView(SwitchStateLayout.VIEW_LOADING)

        CookieTools.getCookieService()!!.getNavi()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    mData.clear()

                    mData.addAll(it.data)

                    mView!!.expandable_layout.setAdapter(DiscoverNaviAdapter(requireContext(), mData))
                }, {
                    mView!!.switch_state.showView(SwitchStateLayout.VIEW_ERROR)
                }, {
                    mView!!.switch_state.showContentView()
                })
    }
}