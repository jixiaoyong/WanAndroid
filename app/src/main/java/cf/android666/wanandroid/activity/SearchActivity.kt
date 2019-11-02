package cf.android666.wanandroid.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import cf.android666.mylibrary.view.SwitchStateLayout
import cf.android666.wanandroid.R
import cf.android666.wanandroid.adapter.PostArticleAdapter
import cf.android666.wanandroid.api.cookie.CookieTools
import cf.android666.wanandroid.base.BaseActivity
import cf.android666.wanandroid.base.toast
import cf.android666.wanandroid.bean.BaseArticlesBean
import cf.android666.wanandroid.utils.SuperUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by jixiaoyong on 2018/2/25.
 */

class SearchActivity : BaseActivity() {

    var mData: ArrayList<BaseArticlesBean> = arrayListOf()

    var key: String = ""

    private var currentPage = 0

    private var pageCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        switch_state.showView(SwitchStateLayout.VIEW_EMPTY)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        recycler_view.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = PostArticleAdapter(mData, false,
                {
                    SuperUtil.startActivity(baseContext, ContentActivity::class.java, it)
                }) { _, _ ->

        }
        recycler_view.setOnFootListener {

            if (currentPage < pageCount) {
                search(key, ++currentPage)
            }
        }
        key = intent.getStringExtra(SearchManager.QUERY)

        search(key)
    }

    private fun search(searchKey: String) {
        search(searchKey, 0)
    }

    private fun search(searchKey: String, page: Int) {
        switch_state.showView(SwitchStateLayout.VIEW_LOADING)

        CookieTools.getCookieService()!!
                .search(page, searchKey)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (recycler_view == null) {
                        return@subscribe
                    }

                    if (it.data.datas.isEmpty()) {
                        toast("${getString(R.string.tips_cannot_find)}$key")
                    }

                    when (page) {

                        0 -> {
                            mData.clear();mData.addAll(it.data.datas)
                        }

                        else -> mData.addAll(it.data.datas)
                    }

                    pageCount = it.data.pageCount

                    currentPage = it.data.curPage

                    recycler_view.adapter?.notifyDataSetChanged()
                }, {
                    switch_state.showView(SwitchStateLayout.VIEW_ERROR)
                }, {
                    switch_state.showContentView()
                })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        var searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
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