package io.github.jixiaoyong.wanandroid.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
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
import io.github.jixiaoyong.wanandroid.databinding.ActivityContentBinding
import io.github.jixiaoyong.wanandroid.utils.CommonConstants

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: 文章查看页面
 */
class ContentActivity : BaseActivity() {

    private var clipboardManager: ClipboardManager? = null
    private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?

        setupFakeStateBar(binding.stateBarView)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.toolbar.dismissPopupMenus()
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.contentRefresh -> {
                    binding.webView.reload()
                }
                R.id.contentCopy -> {
                    val clipData = ClipData.newPlainText("Label", binding.webView.url)
                    clipboardManager?.setPrimaryClip(clipData)
                    toast(getString(R.string.copy_succeeded))
                }
                R.id.contentShare -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, binding.webView.url)
                    startActivity(intent)
                }
                R.id.contentOpen -> {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(binding.webView.url)
                    startActivity(intent)
                }
            }
            true
        }

        val url = intent.getStringExtra(CommonConstants.ACTION_URL)
        Logger.d("url:$url")

        url?.let { binding.webView.loadUrl(it) }
        val webViewSettings = binding.webView.settings
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

        // 不允许WebView使用File协议，防止隐私泄露
        webViewSettings.allowFileAccess = false
        webViewSettings.allowFileAccessFromFileURLs = false
        webViewSettings.allowUniversalAccessFromFileURLs = false

        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let { view?.loadUrl(it) }
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding.progressBar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }
        }
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                binding.toolbar.title = title
                Logger.d("title:$title")
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                Logger.d("progress:$newProgress")
                binding.progressBar.progress = newProgress
            }
        }
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        binding.webView.onPause()
    }

    override fun onDestroy() {
        binding.webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        binding.webView.clearHistory()
        (binding.webView.parent as ViewGroup).removeView(binding.webView)
        binding.webView.destroy()
        super.onDestroy()
    }
}
