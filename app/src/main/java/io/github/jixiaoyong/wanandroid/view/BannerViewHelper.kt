package io.github.jixiaoyong.wanandroid.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.github.jixiaoyong.wanandroid.R
import kotlinx.android.synthetic.main.view_banner_image_fragment.view.*

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-18
 * description: todo
 */
object BannerViewHelper {

    fun initImageBannerOf(size: Int = 0, loadImageViewResource: (ImageView, Int) -> Unit): ArrayList<Fragment> {
        val fragmentList = arrayListOf<Fragment>()
        for (i in 0 until size) {
            val fragment = ImageViewFragment(loadImageViewResource, i)
            fragmentList.add(fragment)
        }
        return fragmentList
    }

    class ImageViewFragment : Fragment {

        private var loadImageViewResource: ((ImageView, Int) -> Unit)? = null
        private var index = 0

        constructor() : super()

        constructor(loadImageViewResource: (ImageView, Int) -> Unit,
                    index: Int) : super() {
            this.loadImageViewResource = loadImageViewResource
            this.index = index
        }


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.view_banner_image_fragment, null, false)
            loadImageViewResource?.invoke(view.image, index)
            return view
        }
    }


    class Builder(private val bannerView: BannerView) {

        private lateinit var fragmentManager: FragmentManager
        private var fragments: List<Fragment> = arrayListOf()
        private var indicatorDescList: List<String>? = null

        fun setFragmentManager(fragmentManager: FragmentManager): Builder {
            this.fragmentManager = fragmentManager
            return this
        }

        /**
         * Banner界面的内容
         */
        fun addFragments(fragments: List<Fragment>): Builder {
            this.fragments = fragments
            return this
        }

        fun addIndicatorDesc(indicatorDescList: List<String>): Builder {
            this.indicatorDescList = indicatorDescList
            return this
        }

        fun build() {
            bannerView.setViewsAndIndicator(fragmentManager, fragments, indicatorDescList)
        }
    }

}