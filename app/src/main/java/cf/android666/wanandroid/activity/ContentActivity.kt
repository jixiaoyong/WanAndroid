package cf.android666.wanandroid.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cf.android666.wanandroid.R
import cf.android666.wanandroid.utils.SuperUtil
import kotlinx.android.synthetic.main.activity_content.*
import cf.android666.wanandroid.base.BaseActivity


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


        SuperUtil.loadUrl(web_view,intent.getStringExtra("url"))


    }

}