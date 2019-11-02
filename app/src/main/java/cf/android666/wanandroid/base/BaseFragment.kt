package cf.android666.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by jixiaoyong on 2018/2/25.
 */
abstract class BaseFragment : androidx.fragment.app.Fragment() {

    protected abstract var layoutId: Int

    private var isFirstInit = true

    protected var mView: View? = null

    // 在viewpager中
    // 在自身/相邻fragment显示的时候就已经执行了，
    // 在自身/相邻fragment由都不可见（会执行onDestroyView）转可见时执行onCreateView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //复用view
        if (mView == null) {

            mView = inflater?.inflate(layoutId, container, false)

        }

        //如果是第一次初始化，则对其进行一些必要的初始化操作
        if (isFirstInit) {

            onCreateViewState(savedInstanceState)

            isFirstInit = false

        }

        //viewpager第一项
        if (userVisibleHint) {

            lazyLoadData()

        }

        return mView
    }

    protected abstract  fun onCreateViewState(savedInstanceState: Bundle?)

    // 会在onCreateView之前、之后执行多次
    // This method may be called outside of the fragment lifecycle.
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (mView != null && isVisibleToUser) {

            lazyLoadData()

        } else if (mView != null && !isVisibleToUser){

            stopLoadData()
        }

    }

    //view 不可见时停止加载数据
    protected open fun stopLoadData() {}

    //每次可见时加载数据
    protected open fun lazyLoadData(){}

}