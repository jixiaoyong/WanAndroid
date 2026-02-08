package io.github.jixiaoyong.wanandroid.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-07
 * description: todo
 */
abstract class BaseAdapter<T : BaseAdapter.ViewHolder> : RecyclerView.Adapter<T>() {


    open class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}