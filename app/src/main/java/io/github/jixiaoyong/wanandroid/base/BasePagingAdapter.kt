package io.github.jixiaoyong.wanandroid.base

import android.view.View
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-15
 * description: todo
 */
abstract class BasePagingAdapter<T : Any, H : BaseViewHolder>(differCallback: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, H>(differCallback)

abstract class BasePagingDataAdapter<T : Any, H : BaseViewHolder>(differCallback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, H>(differCallback)

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view)
