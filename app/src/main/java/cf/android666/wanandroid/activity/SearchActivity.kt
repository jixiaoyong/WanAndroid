package cf.android666.wanandroid.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Toast
import cf.android666.wanandroid.R
import cf.android666.wanandroid.R.id.recycler_view
import cf.android666.wanandroid.R.id.toolbar
import cf.android666.wanandroid.adapter.PostArticleAdapter
import cf.android666.wanandroid.base.BaseActivity
import cf.android666.wanandroid.bean.BaseArticlesBean
import cf.android666.wanandroid.cookie.CookieTools
import cf.android666.wanandroid.utils.SuperUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by jixiaoyong on 2018/2/25.
 */

class SearchActivity : BaseActivity() {

    var mData : ArrayList<BaseArticlesBean> = arrayListOf()

    var key :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener { finish() }

        recycler_view.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)

        recycler_view.adapter = PostArticleAdapter(baseContext,mData,
                {
                    SuperUtil.startActivity(baseContext,ContentActivity::class.java,it)
                },{
            position, isSelected ->

        })

        key = intent.getStringExtra(SearchManager.QUERY)

        search(key)

    }

    private fun search(searchKey: String) {
        search(searchKey, 0)
    }

    private fun search(searchKey: String,page:Int) {

        CookieTools.getCookieService()!!
                .search(page,searchKey)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (recycler_view == null){
                        return@subscribe
                    }

                    if (it.data.datas.isEmpty()) {
                        Toast.makeText(baseContext,"未找到$key",Toast.LENGTH_SHORT).show()
                    }

                    mData.clear()
                    mData.addAll(it.data.datas)
                    recycler_view.adapter.notifyDataSetChanged()
                }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)

        var searchManager= getSystemService(Context.SEARCH_SERVICE) as SearchManager

        var searchView: SearchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        return super.onCreateOptionsMenu(menu)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        key = intent!!.getStringExtra(SearchManager.QUERY)

        search(key)
    }

}