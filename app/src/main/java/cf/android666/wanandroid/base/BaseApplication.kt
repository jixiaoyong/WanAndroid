package cf.android666.wanandroid.base

import android.app.Application
import cf.android666.wanandroid.api.cookie.Preference
import cf.android666.wanandroid.utils.SharePreference
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * Created by jixiaoyong on 2018/2/28.
 */
class BaseApplication:Application(){

    override fun onCreate() {


        super.onCreate()

        Logger.addLogAdapter(AndroidLogAdapter())

        Preference.setContext(applicationContext)

        SharePreference.setContext(this)

        val versionCode = applicationContext.packageManager.getPackageInfo(packageName, 0).versionCode

        SharePreference.saveKV(SharePreference.VERSION_CODE,versionCode)

    }

}