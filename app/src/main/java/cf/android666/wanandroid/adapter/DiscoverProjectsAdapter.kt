package cf.android666.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cf.android666.wanandroid.R
import cf.android666.wanandroid.bean.BaseArticlesBean
import cf.android666.wanandroid.fragment.DiscoverProjectsFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_discover_projects.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jixiaoyong on 2018/2/28.
 */
class DiscoverProjectsAdapter(private val context: DiscoverProjectsFragment,
                              private val data: List<BaseArticlesBean>,
                              private val itemListener: ((url: String) -> Unit),
                              private val imgBtnListener: ((view: View) -> Unit))
    : RecyclerView.Adapter<DiscoverProjectsAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_discover_projects, parent, false)
        return MViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.itemView.title.text = data[position].title
        holder.itemView.summary.text = data[position].desc
        holder.itemView.time.text = SimpleDateFormat("yyyy-MM-dd")
                .format(Date(data[position].publishTime))
        holder.itemView.author.text = data[position].author

        val request = RequestOptions().placeholder(R.drawable.nothing)
        val imgUrl = data[position].envelopePic
        Glide.with(context)
                .load(imgUrl)
                .apply(request)
                .into(holder.itemView.image)

        holder.itemView.setOnClickListener {
            itemListener.invoke(data[position].link)
        }

    }


    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            val params: ViewGroup.LayoutParams = view.layoutParams
            params.height = (Math.random() * 300 + 600).toInt()
            view.layoutParams = params
        }
    }

}