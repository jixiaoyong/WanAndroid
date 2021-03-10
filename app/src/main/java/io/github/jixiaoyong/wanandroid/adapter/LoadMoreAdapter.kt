package io.github.jixiaoyong.wanandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.jixiaoyong.wanandroid.databinding.ItemLoadMoreBinding

/**
 * description ： TODO
 * @author : shayn
 * @email : shayn@yeahka.com
 * @date : 2021/3/10
 */
class LoadMoreAdapter(private val onRetry: () -> Unit) : LoadStateAdapter<LoadMoreAdapter.VH>() {

    class VH(val loadMore: ItemLoadMoreBinding) : RecyclerView.ViewHolder(loadMore.root)

    override fun onBindViewHolder(holder: VH, loadState: LoadState) {
        holder.loadMore.progressBar.isVisible = loadState is LoadState.Loading
        holder.loadMore.retryButton.isVisible = loadState is LoadState.Error
        holder.loadMore.tipsTv.isVisible = loadState is LoadState.Error
        if (loadState is LoadState.Error) {
            holder.loadMore.tipsTv.text = loadState.error.message
        }
        holder.loadMore.retryButton.setOnClickListener { onRetry() }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        VH(ItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context)))
}
