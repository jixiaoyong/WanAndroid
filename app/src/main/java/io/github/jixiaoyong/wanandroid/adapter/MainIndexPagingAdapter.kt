package io.github.jixiaoyong.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import cf.android666.applibrary.view.Toast
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam
import io.github.jixiaoyong.wanandroid.base.BasePagingAdapter
import io.github.jixiaoyong.wanandroid.base.BasePagingDataAdapter
import io.github.jixiaoyong.wanandroid.base.BaseViewHolder
import io.github.jixiaoyong.wanandroid.databinding.ItemMainIndexPostBinding
import io.github.jixiaoyong.wanandroid.utils.NetUtils
import kotlin.concurrent.thread

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-15
 * description: todo
 */
class MainIndexPagingAdapter(
    private val updateIndexPostCollectState: (DataIndexPostParam) -> Unit,
    private val onViewHolder: ((View, DataIndexPostParam) -> Unit)? = null,
    private val isLogin: (() -> Boolean)? = null,
    private val notLoginFunc: (() -> Unit)? = null
) :
    BasePagingDataAdapter<DataIndexPostParam, ViewHolder>(Diff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_main_index_post, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position) ?: return
        holder.dataBinding.data = data
        onViewHolder?.invoke(holder.itemView, data)

        if (!data.link.isNullOrBlank()) {
            holder.dataBinding.titleTv.setOnClickListener {
                NetUtils.loadUrl(holder.itemView.context, data.link)
            }
        }
        holder.dataBinding.favoriteTv.setOnClickListener {
            if (isLogin?.invoke() == true) {
                val newData = data.copy(collect = !data.collect)
                updateIndexPostCollectState(newData)
            } else {
                notLoginFunc?.invoke() ?: Toast.show(it.context.getString(R.string.tips_plz_login))
            }
        }
    }
}

class ViewHolder(val dataBinding: ItemMainIndexPostBinding) : BaseViewHolder(dataBinding.root)

class MainIndexPagingAdapterOld(
    private val updateIndexPostCollectState: (DataIndexPostParam) -> Unit,
    private val onViewHolder: ((View, DataIndexPostParam) -> Unit)? = null,
    private val isLogin: (() -> Boolean)? = null
) :
    BasePagingAdapter<DataIndexPostParam, ViewHolder>(Diff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_main_index_post, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position) ?: return
        holder.dataBinding.data = data
        onViewHolder?.invoke(holder.itemView, data)

        if (!data.link.isNullOrBlank()) {
            holder.dataBinding.titleTv.setOnClickListener {
                NetUtils.loadUrl(holder.itemView.context, data.link)
            }
        }
        holder.dataBinding.favoriteTv.setOnClickListener {
            if (isLogin?.invoke() == true) {
                thread {
                    val newData = data.copy(collect = !data.collect)
                    updateIndexPostCollectState(newData)
                }
            } else {
                Toast.show(it.context.getString(R.string.tips_plz_login))
            }
        }
    }
}

class Diff : DiffUtil.ItemCallback<DataIndexPostParam>() {

    override fun areItemsTheSame(oldItem: DataIndexPostParam, newItem: DataIndexPostParam): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataIndexPostParam, newItem: DataIndexPostParam): Boolean {
        return oldItem == newItem
    }
}
