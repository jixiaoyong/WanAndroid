package cf.android666.wanandroid.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.bean.BaseArticlesBean
import kotlinx.android.synthetic.main.item_index_post.view.*

/**
 * Created by jixiaoyong on 2018/2/27.
 */
class IndexFavoriteAdapter(private val context :Context,
                           private val  data: ArrayList<BaseArticlesBean>,
                           private val itemListener: ((url: String) -> Unit),
                           private val  imgBtnListener: ((position:Int) -> Unit))
    : RecyclerView.Adapter<IndexFavoriteAdapter.MViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MViewHolder {

        var view = View.inflate(this.context, R.layout.item_index_favorite, null)

        return MViewHolder(view)
    }

    override fun getItemCount(): Int {

        return this.data.size
    }

    override fun onBindViewHolder(holder: MViewHolder?, position: Int) {

        holder!!.itemView.title.text = this.data[position].title

        holder.itemView.author.text = "作者：${this.data!![position].author}"

        holder.itemView.time.text = "时间：${this.data!![position].niceDate}"

        holder.itemView.imageButton.isSelected = true


        holder.itemView.setOnClickListener{

            this.itemListener.invoke(this.data[position].link)

        }

        holder.itemView.imageButton.setOnClickListener{

            holder.itemView.imageButton.isSelected = !holder.itemView.imageButton.isSelected

            this.imgBtnListener.invoke(position)
        }

    }


    class MViewHolder(view:View) : RecyclerView.ViewHolder(view){
         init {
         }
    }
}