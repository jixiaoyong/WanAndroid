package cf.android666.wanandroid.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import cf.android666.wanandroid.R
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
object SuperUtil {

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

    fun <T> startActivity(context: Context, clazz: Class<T>, url: String) {

        var intent = Intent(context, clazz)

        intent.putExtra("url", url)

        context.startActivity(intent)
    }

    fun update(context: Context, needToast: Boolean) {
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

                    if (needToast) {
                        when {
                            it.errorCode < 0 -> Toast.makeText(context, context.getString(R.string.tips_failed_to_check_upgrade)
                                    , Toast.LENGTH_SHORT).show()
                            versionCode < it.versionCode -> updateApp(context, it)
                            else -> Toast.makeText(context, context.getString(R.string.tips_alreadl_laster), Toast.LENGTH_SHORT).show()
                        }
                    } else if (versionCode < it.versionCode) {
                        updateApp(context, it)
                    }

                }

    }

    private fun updateApp(context: Context, it: UpdateInfoBean) {

        AlertDialog.Builder(context)
                .setMessage(it.summary)
                .setTitle(context.getString(R.string.title_upgrade_app))
                .setPositiveButton(context.getString(R.string.sure_upgrade)) { dialog, which ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                    intent.addCategory("android.intent.category.DEFAULT")
                    context.startActivity(intent)
                    dialog.dismiss()
                }.setNegativeButton(context.getString(R.string.cancel_update)) { dialog, which ->
                    dialog.dismiss()
                }.create().show()

    }

}