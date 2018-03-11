package cf.android666.wanandroid.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.bean.BaseArticlesBean
import cf.android666.wanandroid.bean.DiscoverProjectItemBean
import cf.android666.wanandroid.utils.SuperUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.item_discover_projects.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jixiaoyong on 2018/2/28.
 */
class DiscoverProjectsAdapter(private val context: Context,
                              private val data: List<BaseArticlesBean>,
                              private val itemListener: ((url: String) -> Unit),
                              private val imgBtnListener: ((view: View) -> Unit))
    : RecyclerView.Adapter<DiscoverProjectsAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MViewHolder {

        var view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_discover_projects, parent, false)

        return MViewHolder(view)
    }

    override fun getItemCount(): Int {

        return data.size

    }

    override fun onBindViewHolder(holder: MViewHolder?, position: Int) {

        holder!!.itemView.title.text = data[position].title

        holder!!.itemView.summary.text = data[position].desc

        holder!!.itemView.time.text = SimpleDateFormat("yyyy-MM-dd")
                .format(Date(data[position].publishTime))

        holder!!.itemView.author.text = data[position].author

        var request = RequestOptions().placeholder(R.drawable.nothing)

        Glide.with(context)
                .load(data[position].envelopePic)
                .apply(request)
                .into(holder.itemView.image)

        holder!!.itemView.setOnClickListener {

            itemListener!!.invoke(data[position].link!!)
        }

    }


    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {

            var params: ViewGroup.LayoutParams = view.layoutParams

            params.height = (Math.random() * 300 + 600).toInt()

            view.layoutParams = params

        }

    }

}