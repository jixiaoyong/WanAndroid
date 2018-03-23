package cf.android666.wanandroid.base

import android.app.Application
import android.content.ComponentCallbacks2
import cf.android666.wanandroid.api.cookie.Preference
import cf.android666.wanandroid.utils.SharePreference
import com.bumptech.glide.Glide
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.squareup.leakcanary.LeakCanary

/**
 * Created by jixiaoyong on 2018/2/28.
 */

class BaseApplication:Application(){

    override fun onCreate() {


        super.onCreate()

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }

        LeakCanary.install(this)

        Logger.addLogAdapter(AndroidLogAdapter())

        Preference.setContext(applicationContext)

        SharePreference.setContext(this)

        val versionCode = applicationContext.packageManager.getPackageInfo(packageName, 0).versionCode

        SharePreference.saveKV(SharePreference.VERSION_CODE,versionCode)

        /**
         * 初始化友盟 common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null)

        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true)

        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(false)

        MobclickAgent.setScenarioType(applicationContext, MobclickAgent.EScenarioType. E_UM_NORMAL)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory()
        }
        Glide.get(this).trimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()

        Glide.get(this).clearMemory()
    }

}