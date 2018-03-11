package cf.android666.wanandroid.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cf.android666.wanandroid.R
import cf.android666.wanandroid.utils.SharePreference

/**
 * Created by jixiaoyong on 2018/3/11.
 * email:jixiaoyong1995@gmail.com
 */
open class BaseActivity:AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var isNightMode = SharePreference.getV<Boolean>(SharePreference.IS_NIGHT_MODE, false)

        var themeId = if (isNightMode) R.style.AppTheme_NoActionBar_Night
        else R.style.AppTheme_NoActionBar

        setTheme(themeId)
    }
}