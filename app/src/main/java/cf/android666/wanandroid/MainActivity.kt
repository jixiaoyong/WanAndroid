package cf.android666.wanandroid

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import cf.android666.wanandroid.adapter.MFragmentViewPagerAdapter
import cf.android666.wanandroid.api.UpdateService
import cf.android666.wanandroid.base.BaseActivity
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.bean.UpdateInfoBean
import cf.android666.wanandroid.fragment.AboutFragment
import cf.android666.wanandroid.fragment.DiscoverFragment
import cf.android666.wanandroid.fragment.IndexFragment
import cf.android666.wanandroid.utils.SharePreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

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

    fun update() {
        Retrofit.Builder()
                .baseUrl(UpdateService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(UpdateService::class.java)
                .getUpdateInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    var versionCode = SharePreference.getV<Int>(SharePreference.VERSION_CODE, -1)

                    when {

                        it.errorCode < 0 -> Toast.makeText(baseContext, "检查更新失败，请稍后重试~"
                                , Toast.LENGTH_SHORT).show()

                        versionCode > it.versionCode -> updateApp(it)

                        else -> Toast.makeText(baseContext, "已经是最新啦", Toast.LENGTH_SHORT).show()
                    }

                }

    }

    private fun updateApp(it: UpdateInfoBean) {

        AlertDialog.Builder(baseContext)
                .setMessage(it.summary)
                .setTitle("更新程序")
                .setPositiveButton("更新") { dialog, which ->

                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))

                    intent.addCategory("android.intent.category.DEFAULT")

                    baseContext.startActivity(intent)

                    dialog.dismiss()

                }.setNegativeButton("取消") { dialog, which ->

                    dialog.dismiss()

                }.create().show()

    }

}
