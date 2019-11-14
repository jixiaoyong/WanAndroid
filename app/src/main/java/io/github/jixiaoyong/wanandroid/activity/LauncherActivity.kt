package io.github.jixiaoyong.wanandroid.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.base.BaseActivity
import io.github.jixiaoyong.wanandroid.base.toast
import io.github.jixiaoyong.wanandroid.utils.DatabaseUtils
import io.github.jixiaoyong.wanandroid.utils.NetUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: 第一个启动的页面，显示APP版本信息
 */
class LauncherActivity : BaseActivity() {

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

//        val dialog: AlertDialog = AlertDialog.Builder(this)
//                .setView(R.layout.dialog_update)
//                .create()
//        dialog.window?.setBackgroundDrawable(ColorDrawable(0))
//        dialog.show()

        context = this

    }

    override fun onResume() {
        super.onResume()
        launch {
            val isLogin = withContext(Dispatchers.IO) {
                isLogin()
            }

            val intent = if (isLogin) {
                Intent(context, MainActivity::class.java)
            } else {
                Intent(context, LoginRegisterActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }

    suspend fun isLogin(): Boolean {
        val result = NetUtils.wanAndroidApi.getCoinInfo()
        Logger.d("result:$result")
        return try {
            if (result.errorCode == NetUtils.ErrorCode.SUCCEEDED) {
                Logger.e("is login")
                true
            } else {
                Logger.e("failed to login,clean the cookies")
                DatabaseUtils.database.cookiesDao().cleanAll()
                withContext(Dispatchers.Main) {
                    toast(getString(R.string.tips_cookies_expire))
                }
                false
            }
        } catch (e: Exception) {
            Logger.e("network error")
            e.printStackTrace()
            false
        }
    }
}