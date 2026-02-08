package io.github.jixiaoyong.wanandroid.view

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.base.BaseDialog


/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-14
 * description: todo
 */
class ProgressDialog(context: Context, private val text: String? = null) : BaseDialog(context) {

    private lateinit var progressTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val progressView = layoutInflater.inflate(R.layout.view_progress, null, false)
        progressTextView = progressView.findViewById<TextView>(R.id.progressText)
        window?.setBackgroundDrawable(ColorDrawable(0))

        setProgressText(text)
        setContentView(progressView)
    }

    fun setProgressText(text: String?) {
        text?.let {
            progressTextView.text = it
            progressTextView.visibility = View.VISIBLE
        }
    }

}