package cf.android666.wanandroid.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cf.android666.wanandroid.R
import cf.android666.wanandroid.utils.EventInterface
import cf.android666.wanandroid.utils.SharePreference
import com.squareup.leakcanary.LeakCanary

import com.umeng.analytics.MobclickAgent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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

        EventBus.getDefault().register(this)
    }

    override fun onResume() {

        super.onResume()

        MobclickAgent.onResume(this)

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun mainSubscribe(msgEvent:EventInterface){

    }

    override fun onPause() {

        super.onPause()

        MobclickAgent.onPause(this)
    }

    override fun onDestroy() {

        super.onDestroy()

        EventBus.getDefault().unregister(this)
    }
}