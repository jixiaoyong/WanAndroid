package io.github.jixiaoyong.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam
import io.github.jixiaoyong.wanandroid.base.BasePagingAdapter
import io.github.jixiaoyong.wanandroid.base.BaseViewHolder
import io.github.jixiaoyong.wanandroid.databinding.ItemMainProjectBinding
import io.github.jixiaoyong.wanandroid.utils.NetUtils
import kotlinx.android.synthetic.main.item_main_index_post.view.*
import kotlin.concurrent.thread

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-15
 * description: todo
 */
class MainProjectPagingAdapter(private val updateIndexPostCollectState: (DataIndexPostParam) -> Unit,
                               private val onViewHolder: ((View, DataIndexPostParam) -> Unit)? = null)
    : BasePagingAdapter<DataIndexPostParam, MainProjectPagingAdapter.ViewHolder>(Diff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_main_project, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position) ?: return
        holder.dataBinding.data = data
        onViewHolder?.invoke(holder.itemView, data)

        if (!data.link.isNullOrBlank()) {
            holder.itemView.titleTv.setOnClickListener {
                NetUtils.loadUrl(holder.itemView.context, data.link)
            }
        }
        holder.itemView.favoriteTv.setOnClickListener {
            Logger.d("before: data.collect ${data.collect},!data.collect:${!data.collect}")
            data.collect = !data.collect
            Logger.d("before:  data.collect = !data.collect ${data.collect}")

            thread {
                Logger.d("thread data.collect = !data.collect ${data.collect}")
                updateIndexPostCollectState(data)
            }
        }
    }

    class ViewHolder(val dataBinding: ItemMainProjectBinding) : BaseViewHolder(dataBinding.root)
}