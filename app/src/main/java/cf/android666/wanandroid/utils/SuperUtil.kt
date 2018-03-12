package cf.android666.wanandroid.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import cf.android666.wanandroid.api.UpdateService
import cf.android666.wanandroid.bean.UpdateInfoBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by jixiaoyong on 2018/2/7.
 */
object SuperUtil{

    fun toast(context: Context, msg: String) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

    fun loadUrl(webView: WebView, url: String) {

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view!!.loadUrl(url)
                return true
            }
        }

        webView.settings.javaScriptEnabled = true

        webView.loadUrl(url)
    }

    fun <T> startActivity(context: Context,clazz:Class<T>,url: String){

        var intent = Intent(context,clazz)

        intent.putExtra("url", url)

        context.startActivity(intent)
    }

    fun update(context: Context) {
        Retrofit.Builder()
                .baseUrl(UpdateService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(UpdateService::class.java)
                .getUpdateInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    var versionCode = SharePreference.getV<Int>(SharePreference.VERSION_CODE, -1)

                    when {

                        it.errorCode < 0 -> Toast.makeText(context, "检查更新失败，请稍后重试~"
                                , Toast.LENGTH_SHORT).show()

                        versionCode > it.versionCode -> updateApp(context,it)

                        else -> Toast.makeText(context, "已经是最新啦", Toast.LENGTH_SHORT).show()
                    }

                }

    }

    private fun updateApp(context: Context,it: UpdateInfoBean) {

        AlertDialog.Builder(context)
                .setMessage(it.summary)
                .setTitle("更新程序")
                .setPositiveButton("更新") { dialog, which ->

                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))

                    intent.addCategory("android.intent.category.DEFAULT")

                    context.startActivity(intent)

                    dialog.dismiss()

                }.setNegativeButton("取消") { dialog, which ->

                    dialog.dismiss()

                }.create().show()

    }

}