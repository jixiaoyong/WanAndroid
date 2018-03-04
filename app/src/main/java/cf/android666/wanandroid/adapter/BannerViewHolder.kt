package cf.android666.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cf.android666.wanandroid.R
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.bean.IndexBannerBean
import cf.android666.wanandroid.utils.SuperUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.zhouwei.mzbanner.holder.MZViewHolder
import kotlinx.android.synthetic.main.fragment_index_post_banner.view.*

/**
 * Created by jixiaoyong on 2018/3/4.
 * email:jixiaoyong1995@gmail.com
 */

class BannerViewHolder : MZViewHolder<IndexBannerBean.DataBean> {

    init {

    }

    private var mImageView: ImageView? = null

    private var mTextView: TextView? = null

    override fun createView(context: Context): View {

        // 返回页面布局
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_index_post_banner, null)

        mImageView = view.image

        mTextView = view.title

        return view
    }

    override fun onBind(p0: Context?, p1: Int, p2: IndexBannerBean.DataBean?) {

        Glide.with(p0)
                .load(p2!!.imagePath)
                .into(mImageView)

        mImageView!!.setOnClickListener {

            SuperUtil.startActivity(p0!!,ContentActivity::class.java,p2.url!!)
        }

        mTextView!!.text = p2!!.title

    }
}