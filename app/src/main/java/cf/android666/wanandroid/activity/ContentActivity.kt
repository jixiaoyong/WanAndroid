package cf.android666.wanandroid.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import cf.android666.wanandroid.R
import cf.android666.wanandroid.utils.SuperUtil
import cf.android666.wanandroid.base.BaseActivity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.*
import cf.android666.mylibrary.view.SwitchStateLayout
import kotlinx.android.synthetic.main.activity_content.*


/**
 * Created by jixiaoyong on 2018/2/25.
 */

class ContentActivity : BaseActivity() {

    var webTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_content)

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener { finish() }

        switch_state.showView(SwitchStateLayout.VIEW_EMPTY)

        SuperUtil.loadUrl(web_view, intent.getStringExtra("url"))

        web_view.settings.setSupportZoom(true)
        web_view.settings.builtInZoomControls = true

        web_view.webViewClient = object : WebViewClient() {

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                switch_state.showView(SwitchStateLayout.VIEW_ERROR)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                switch_state.showView(SwitchStateLayout.VIEW_LOADING)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                switch_state.showContentView()
                webTitle = view!!.title
                toolbar.title = view!!.title
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_content, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {

            R.id.refresh -> web_view.reload()

            R.id.open_sys ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(intent.getStringExtra("url"))))

            R.id.share -> {
                var share_intent = Intent()

                share_intent.action = Intent.ACTION_SEND

                share_intent.type = "text/plain"

                share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享")

                share_intent.putExtra(Intent.EXTRA_TEXT, "这篇文章不错，快来看看。${intent.getStringExtra("url")}" +
                        "\n分享自【WanAndroid】")

                share_intent = Intent.createChooser(share_intent, "分享")

                startActivity(share_intent)
            }

        }

        return super.onOptionsItemSelected(item)
    }

}