package io.github.jixiaoyong.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.api.bean.DataHotKeyParam

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-12-14
 * description: todo
 */
class SearchAdapter(private val data: List<DataHotKeyParam>, private val onClickListener: ((text: String) -> Unit)?) :
    RecyclerView.Adapter<SearchAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.searchItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val text = data[position].name
        holder.textView.text = text
        holder.textView.setOnClickListener {
            onClickListener?.invoke(text)
        }
    }
}
