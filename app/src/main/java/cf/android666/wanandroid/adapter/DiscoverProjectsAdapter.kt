package cf.android666.wanandroid.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.bean.DiscoverProjectItemBean
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.item_discover_projects.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jixiaoyong on 2018/2/28.
 */
class DiscoverProjectsAdapter(context :Context, data: List<DiscoverProjectItemBean.DataBean.DatasBean>,
                              itemListener: ((url: String) -> Unit),
                              imgBtnListener: ((view: View) -> Unit))
    : RecyclerView.Adapter<DiscoverProjectsAdapter.MViewHolder>(){

    private var mData : List<DiscoverProjectItemBean.DataBean.DatasBean> = arrayListOf()

    private var mContext : Context? = null

    private var mItemListener: ((url: String) -> Unit)? = null

    private var mImgBtnListener: ((view: View) -> Unit)? = null

    init {

        mData = data

        mContext = context

        mItemListener = itemListener

        mImgBtnListener = imgBtnListener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MViewHolder {

        var view = View.inflate(mContext, R.layout.item_discover_projects, null)

        return MViewHolder(view)
    }

    override fun getItemCount(): Int {

        return mData.size

    }

    override fun onBindViewHolder(holder: MViewHolder?, position: Int) {

        holder!!.itemView.title.text = mData[position].title

        holder!!.itemView.summary.text = mData[position].desc

        holder!!.itemView.time.text = SimpleDateFormat("yyyy-MM-dd")
                .format(Date(mData[position].publishTime))

        holder!!.itemView.author.text = mData[position].author

        Glide.with(mContext!!).load(mData[position].envelopePic).into(holder.itemView.image)

    }



    class MViewHolder(view:View) : RecyclerView.ViewHolder(view){

    }
}