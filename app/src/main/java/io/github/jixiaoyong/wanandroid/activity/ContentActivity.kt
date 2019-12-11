package io.github.jixiaoyong.wanandroid.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.base.BaseActivity
import io.github.jixiaoyong.wanandroid.base.toast
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import kotlinx.android.synthetic.main.activity_content.*


/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: 文章查看页面
 */
class ContentActivity : BaseActivity() {

    private var clipboardManager: ClipboardManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?

        setupFakeStateBar(stateBarView)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        toolbar.dismissPopupMenus()
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.contentRefresh -> {
                    webView.reload()
                }
                R.id.contentCopy -> {
                    val clipData = ClipData.newPlainText("Label", webView.url)
                    clipboardManager?.primaryClip = clipData
                    toast(getString(R.string.copy_succeeded))
                }
                R.id.contentShare -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, webView.url)
                    startActivity(intent)
                }
                R.id.contentOpen -> {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(webView.url)
                    startActivity(intent)
                }
            }
            true
        }

        val url = intent.getStringExtra(CommonConstants.ACTION_URL)
        Logger.d("url:$url")

        webView.loadUrl(url)
        val webViewSettings = webView.settings
        webViewSettings.javaScriptEnabled = false

        webViewSettings.useWideViewPort = true
        webViewSettings.loadWithOverviewMode = true

        webViewSettings.setSupportZoom(true)
        webViewSettings.builtInZoomControls = true
        webViewSettings.displayZoomControls = false

        webViewSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webViewSettings.allowFileAccess = true
        webViewSettings.javaScriptCanOpenWindowsAutomatically = true
        webViewSettings.loadsImagesAutomatically = true

        //不允许WebView使用File协议，防止隐私泄露
        webViewSettings.allowFileAccess = false
        webViewSettings.allowFileAccessFromFileURLs = false
        webViewSettings.allowUniversalAccessFromFileURLs = false


        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                toolbar.title = title
                Logger.d("title:$title")
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                Logger.d("progress:$newProgress")
                progressBar.progress = newProgress
            }
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onDestroy() {
        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        webView.clearHistory()
        (webView.parent as ViewGroup).removeView(webView)
        webView.destroy()
        super.onDestroy()
    }
}