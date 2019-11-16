package io.github.jixiaoyong.wanandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam
import io.github.jixiaoyong.wanandroid.base.BasePagingAdapter
import io.github.jixiaoyong.wanandroid.base.BaseViewHolder
import io.github.jixiaoyong.wanandroid.databinding.ItemMainIndexPostBinding
import io.github.jixiaoyong.wanandroid.utils.NetUtils
import io.github.jixiaoyong.wanandroid.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.item_main_index_post.view.*
import kotlin.concurrent.thread

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-15
 * description: todo
 */
class MainIndexPagingAdapter(private val mainViewModel: MainViewModel)
    : BasePagingAdapter<DataIndexPostParam, MainIndexPagingAdapter.ViewHolder>(Diff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_main_index_post, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position) ?: return
        holder.dataBinding.data = data
        if (!data.link.isNullOrBlank()) {
            holder.itemView.titleTv.setOnClickListener {
                NetUtils.loadUrl(holder.itemView.context, data.link)
            }
        }
        holder.itemView.favoriteTv.setOnClickListener {
            data.collect = !data.collect
            thread {
                mainViewModel.updateIndexPostCollectState(data)
            }
        }
    }

    class ViewHolder(val dataBinding: ItemMainIndexPostBinding) : BaseViewHolder(dataBinding.root)
}

class Diff : DiffUtil.ItemCallback<DataIndexPostParam>() {

    override fun areItemsTheSame(oldItem: DataIndexPostParam, newItem: DataIndexPostParam): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: DataIndexPostParam, newItem: DataIndexPostParam): Boolean {
        return oldItem.title == newItem.title
    }

}
