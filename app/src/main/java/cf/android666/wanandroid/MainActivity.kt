package cf.android666.wanandroid

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.Menu
import cf.android666.wanandroid.adapter.MFragmentViewPagerAdapter
import cf.android666.wanandroid.base.BaseActivity
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.fragment.AboutFragment
import cf.android666.wanandroid.fragment.DiscoverFragment
import cf.android666.wanandroid.fragment.IndexFragment
import cf.android666.wanandroid.utils.SharePreference
import cf.android666.wanandroid.utils.SuperUtil
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        if (SharePreference.getV(SharePreference.IS_AUTO_UPDATE, false)) {
            SuperUtil.update(this)
        }

        val fragments = arrayListOf<BaseFragment>()

        fragments.add(IndexFragment())
        fragments.add(DiscoverFragment())
        fragments.add(AboutFragment())

        viewpager.adapter = MFragmentViewPagerAdapter(supportFragmentManager, fragments)

        val bottomNavIds = arrayListOf(R.id.index, R.id.discover, R.id.about)

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bottom_nav.selectedItemId = bottomNavIds[position]
            }
        })

        bottom_nav.setOnNavigationItemSelectedListener { it ->
            when (it.itemId) {
                R.id.index -> {
                    viewpager.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.discover -> {
                    viewpager.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.about -> {
                    viewpager.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)

        var searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        var searchView: SearchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        return true
    }



}
