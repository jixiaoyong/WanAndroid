package io.github.jixiaoyong.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.base.BaseAdapter
import kotlinx.android.synthetic.main.item_main_index_post.view.*

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-07
 * description: todo
 */
class MainIndexPostAdapter(val count: Int = 150) : BaseAdapter<MainIndexPostAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_main_index_post, parent, false))
    }

    override fun getItemCount() = count

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.titleTv.text = "item $position"
    }


    class VH(view: View) : BaseAdapter.ViewHolder(view)

}