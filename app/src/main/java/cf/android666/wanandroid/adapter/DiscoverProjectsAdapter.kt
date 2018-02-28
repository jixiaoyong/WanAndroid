package cf.android666.wanandroid.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.bean.DiscoverProjectItemBean
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_discover_projects.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jixiaoyong on 2018/2/28.
 */
class DiscoverProjectsAdapter(context :Context, data: DiscoverProjectItemBean.DataBean,
                              itemListener: ((url: String) -> Unit),
                              imgBtnListener: ((view: View) -> Unit))
    : RecyclerView.Adapter<DiscoverProjectsAdapter.MViewHolder>(){

    private var mData = DiscoverProjectItemBean.DataBean()

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

        return mData.datas.size

    }

    override fun onBindViewHolder(holder: MViewHolder?, position: Int) {

        holder!!.itemView.title.text = mData.datas[position].title

        holder!!.itemView.summary.text = mData.datas[position].desc

        holder!!.itemView.time.text = SimpleDateFormat("yyyy-MM-dd")
                .format(Date(mData.datas[position].publishTime))

        holder!!.itemView.author.text = mData.datas[position].author

        Glide.with(mContext!!).load(mData.datas[position].envelopePic).into(holder.itemView.image)

    }



    class MViewHolder(view:View) : RecyclerView.ViewHolder(view){

    }
}