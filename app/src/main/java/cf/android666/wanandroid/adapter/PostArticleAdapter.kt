package cf.android666.wanandroid.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.bean.BaseArticlesBean
import cf.android666.wanandroid.utils.SharePreference
import kotlinx.android.synthetic.main.item_index_post.view.*

/**
 * Created by jixiaoyong on 2018/2/27.
 */
class PostArticleAdapter(private val context :Context, private val data: ArrayList<BaseArticlesBean>,
                         private val itemListener: ((url: String) -> Unit),
                         private val imgBtnListener: ((position:Int, isSelected:Boolean) -> Unit))
    : RecyclerView.Adapter<PostArticleAdapter.MViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MViewHolder {

        var view = View.inflate(this.context, R.layout.item_index_post, null)

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

        holder.itemView.imageButton.isSelected = data[position].collect

        holder.itemView.imageButton.setOnClickListener{

            var isLogin = SharePreference.getV<Boolean>(SharePreference.IS_LOGIN, false)

            if (isLogin) {

                it.isSelected = !it.isSelected

            }

            this.imgBtnListener!!.invoke(position,it.isSelected)

        }

    }


    class MViewHolder(view:View) : RecyclerView.ViewHolder(view){
         init {
         }
    }
}