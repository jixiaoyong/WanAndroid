package cf.android666.wanandroid.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.bean.IndexArticleBean
import cf.android666.wanandroid.utils.LogTools
import kotlinx.android.synthetic.main.item_index_post.view.*

/**
 * Created by jixiaoyong on 2018/2/27.
 */
class IndexPostArticleAdapter(context :Context, data: IndexArticleBean.DataBean,
                              itemListener: ((url: String) -> Unit),
                              imgBtnListener: ((view: View) -> Unit))
    : RecyclerView.Adapter<IndexPostArticleAdapter.MViewHolder>(){

    private var mData = IndexArticleBean.DataBean()

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

        var view = View.inflate(mContext, R.layout.item_index_post, null)

        return MViewHolder(view)
    }

    override fun getItemCount(): Int {

        return mData.size
    }

    override fun onBindViewHolder(holder: MViewHolder?, position: Int) {

        holder!!.itemView.title.text = mData.datas!![position].title

        holder!!.itemView.author.text = "作者：${mData.datas!![position].author}"

        holder!!.itemView.time.text = "时间：${mData.datas!![position].niceDate}"

        holder!!.itemView.chapter.text = "分类：${mData.datas!![position].chapterName}"

        holder.itemView.setOnClickListener{

            mItemListener!!.invoke(mData.datas!![position].link!!)

        }

        holder.itemView.imageButton.setOnClickListener{

            mImgBtnListener!!.invoke(it)
        }

    }


    class MViewHolder(view:View) : RecyclerView.ViewHolder(view){
         init {
         }
    }
}