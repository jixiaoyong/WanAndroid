package cf.android666.wanandroid.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.bean.BaseArticlesBean
import kotlinx.android.synthetic.main.item_index_post.view.*

/**
 * Created by jixiaoyong on 2018/2/27.
 */
class PostArticleAdapter(private val data: ArrayList<BaseArticlesBean>,
                         private val isImageButtonSelect : Boolean,
                         private val itemListener: ((url: String) -> Unit),
                         private val imgBtnListener: ((view:View,position:Int) -> Unit))
    : RecyclerView.Adapter<PostArticleAdapter.MViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MViewHolder {

        var view = LayoutInflater.from(parent!!.context)
                .inflate( R.layout.item_index_post,parent,false)

        return MViewHolder(view)
    }

    override fun getItemCount(): Int {

        return data.size
    }

    override fun onBindViewHolder(holder: MViewHolder?, position: Int) {

        holder!!.itemView.title.text = data[position].title

        holder.itemView.author.text = "作者：${data[position].author}"

        holder.itemView.time.text = "时间：${data[position].niceDate}"

        holder.itemView.chapter.text = "分类：${data[position].chapterName}"

        holder.itemView.setOnClickListener{

            this.itemListener.invoke(data[position].link)

        }

        if (isImageButtonSelect) {

            holder.itemView.imageButton.isSelected = true

        } else {

            holder.itemView.imageButton.isSelected = data[position].collect
        }

        holder.itemView.imageButton.setOnClickListener{

            this.imgBtnListener!!.invoke(it,position)

        }

    }

    class MViewHolder(view:View) : RecyclerView.ViewHolder(view)
}