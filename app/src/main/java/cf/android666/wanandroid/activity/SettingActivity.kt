package cf.android666.wanandroid.activity

import android.os.Bundle
import cf.android666.wanandroid.R
import cf.android666.wanandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*

/**
 * Created by jixiaoyong on 2018/3/11.
 * email:jixiaoyong1995@gmail.com
 */
class SettingActivity: BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

    }


}