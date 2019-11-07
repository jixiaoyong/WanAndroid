package io.github.jixiaoyong.wanandroid.activity

import android.content.Intent
import android.os.Bundle
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: 第一个启动的页面，显示APP版本信息
 */
class LauncherActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

//        val dialog: AlertDialog = AlertDialog.Builder(this)
//                .setView(R.layout.dialog_update)
//                .create()
//        dialog.window?.setBackgroundDrawable(ColorDrawable(0))
//        dialog.show()

        val isLogin = true
        val context = this

        launch {
            //            delay(3_000)
            delay(1_000)

            val intent = if (isLogin) {
                Intent(context, MainActivity::class.java)
            } else {
                Intent(context, LoginRegisterActivity::class.java)
            }
            startActivity(intent)
            finish()
        }

    }
}