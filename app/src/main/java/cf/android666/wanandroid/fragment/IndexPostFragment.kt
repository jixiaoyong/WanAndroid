package cf.android666.wanandroid.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cf.android666.wanandroid.R
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.adapter.IndexPostArticleAdapter
import cf.android666.wanandroid.api.ApiUrl
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.IndexArticleBean
import cf.android666.wanandroid.utils.DownloadUtil
import cf.android666.wanandroid.utils.LogTools
import cf.android666.wanandroid.utils.SuperUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_index_post.view.*

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class IndexPostFragment : BaseFragment() {

    private var article = IndexArticleBean()

    //todo 内存泄漏风险
    private var handler = @SuppressLint("HandlerLeak")
    object : android.os.Handler() {

        override fun handleMessage(msg: Message?) {
            if (msg!!.what == MSG_WHAT_ARTICLE) {

                if (msg.obj == null) {
                    return
                }

                article = Gson().fromJson<IndexArticleBean>(msg.obj as String)

                if (article.errorCode >= 0) {

                    view!!.recycler_view.adapter.notifyDataSetChanged()

                } else {

                    Toast.makeText(context, article.errorMsg, Toast.LENGTH_SHORT).show()

                }


            } else if (msg!!.what == MSG_WHAT_BANNER) {

            }
        }
    }

    companion object {

        val MSG_WHAT_ARTICLE = 0

        val MSG_WHAT_BANNER = 1

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_index_post, container, false)

        view.recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        view.recycler_view.adapter = IndexPostArticleAdapter(context, article.data, {

            SuperUtil.startActivity(context, ContentActivity::class.java, it)

        }, {

            it.isSelected = !it.isSelected

        })

        DownloadUtil.downloadJson(ApiUrl.atricleUrl, handler, MSG_WHAT_ARTICLE)


        return view
    }

    inline fun <reified T : Any> Gson.fromJson(json: String): T {

        return Gson().fromJson(json, T::class.java)

    }

}

