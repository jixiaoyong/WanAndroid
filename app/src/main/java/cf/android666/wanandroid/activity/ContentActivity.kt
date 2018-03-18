package cf.android666.wanandroid.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cf.android666.wanandroid.R
import cf.android666.wanandroid.utils.SuperUtil
import kotlinx.android.synthetic.main.activity_content.*
import cf.android666.wanandroid.base.BaseActivity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.*
import cf.android666.mylibrary.view.SwitchStateLayout


/**
 * Created by jixiaoyong on 2018/2/25.
 */

class ContentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_content)

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener { finish() }

        switch_state.showView(SwitchStateLayout.VIEW_EMPTY)

        SuperUtil.loadUrl(web_view,intent.getStringExtra("url"))

        web_view.settings.setSupportZoom(true)
        web_view.settings.builtInZoomControls = true

        web_view.webViewClient = object : WebViewClient(){

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

        }

        return super.onOptionsItemSelected(item)
    }

}