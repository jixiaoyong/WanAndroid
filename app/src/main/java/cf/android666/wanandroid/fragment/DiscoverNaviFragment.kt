package cf.android666.wanandroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.api.ApiUrl
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.utils.SuperUtil
import kotlinx.android.synthetic.main.fragment_discover_navi.view.*

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class DiscoverNaviFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_discover_navi, container, false)

        val url = ApiUrl.naviWebUrl

        SuperUtil.loadUrl(view.web_view,url)

        return view
    }
}