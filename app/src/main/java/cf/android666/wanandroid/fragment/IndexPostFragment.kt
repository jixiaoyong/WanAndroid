package cf.android666.wanandroid.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cf.android666.wanandroid.R
import cf.android666.wanandroid.api.ApiUrl
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.IndexArticleBean
import cf.android666.wanandroid.utils.DownloadUtil
import com.google.gson.Gson

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class IndexPostFragment : BaseFragment() {

    //todo 内存泄漏风险
    private var handler = @SuppressLint("HandlerLeak")
    object :android.os.Handler(){

        override fun handleMessage(msg: Message?) {
            if (msg!!.what == MSG_WHAT_ARTICLE) {

                if (msg.obj == null) {
                    return
                }

                var article :IndexArticleBean = Gson().fromJson<IndexArticleBean>(msg.obj as String)



            } else if (msg!!.what == MSG_WHAT_BANNER) {

            }
        }
    }

    companion object {

        public val MSG_WHAT_ARTICLE = 0

        private val MSG_WHAT_BANNER = 1

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_index_post, container, false)

//        view.recycler_view.adapter =

        DownloadUtil.downloadJson(ApiUrl.atricleUrl, handler, MSG_WHAT_ARTICLE)


        return view
    }

    inline fun <reified T : Any> Gson.fromJson(json: String): T {

        return Gson().fromJson(json, T::class.java)

    }

}

