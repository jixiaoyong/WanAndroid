package cf.android666.wanandroid.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import cf.android666.wanandroid.R
import cf.android666.wanandroid.utils.SuperUtil
import kotlinx.android.synthetic.main.activity_content.*
import cf.android666.wanandroid.R.id.toolbar

/**
 * Created by jixiaoyong on 2018/2/25.
 */

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }





    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)

        var menuItem = menu!!.findItem(R.id.app_bar_search)

        val searchView: SearchView = menuItem.actionView as SearchView

        return super.onCreateOptionsMenu(menu)
    }

}