package cf.android666.wanandroid.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import cf.android666.mylibrary.view.SwitchStateLayout
import cf.android666.wanandroid.R
import cf.android666.wanandroid.api.cookie.CookieTools
import cf.android666.wanandroid.base.BaseActivity
import cf.android666.wanandroid.bean.CookieBean
import cf.android666.wanandroid.utils.EventFactory
import cf.android666.wanandroid.utils.SharePreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import cf.android666.wanandroid.MainActivity


/**
 * Created by jixiaoyong on 2018/3/12.
 * email:jixiaoyong1995@gmail.com
 */
class LoginActivity: BaseActivity(){

    private var isSaveNamePwd = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        var isLogin = SharePreference.getV<Boolean>(SharePreference.IS_LOGIN, false)

        if (isLogin) {

            login_box.visibility = View.GONE

            go2MainActivity()

        }


        skip.setOnClickListener {
            go2MainActivity()
        }

        register.setOnCheckedChangeListener{

            _, isChecked ->

            user_password_re.visibility = if (isChecked) View.VISIBLE else View.GONE

            login_btn.text = if (isChecked)"注册" else "登录"
        }

        isSaveNamePwd = SharePreference.getV(SharePreference.IS_SAVE_NAME_PWD,false)

        if (isSaveNamePwd) {

            user_name.text = SharePreference.getV(SharePreference.USER_NAME,"")

            user_password.text = SharePreference.getV(SharePreference.USER_PWD,"")

        }

        save_name_pwd.isChecked = isSaveNamePwd

        save_name_pwd.setOnCheckedChangeListener{

            _, isChecked ->

            isSaveNamePwd = isChecked

            SharePreference.saveKV(SharePreference.IS_SAVE_NAME_PWD,isSaveNamePwd)

        }

        login_btn.setOnClickListener {
            
            val userName = user_name.text.toString()

            val userPwd = user_password.text.toString()

            if (userName.isEmpty()  || userPwd.isEmpty()) {
                Toast.makeText(baseContext,"账号或密码不能为空",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (register.isChecked) {

                val userPwdRe = user_password_re.text.toString()

                register(userName, userPwd, userPwdRe)

            } else {

                login(userName, userPwd)

            }
        }

    }

    private fun login(userName: String,userPwd: String) {

        saveNamePwd(userName, userPwd)

        CookieTools.getCookieService()!!
                .login(userName,userPwd)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    updateLoginState(it, userName,"登录成功")
                }

    }

    private fun register(username: String, password1: String, password2: String) {

        saveNamePwd(username, password1)

        CookieTools.getCookieService()!!
                .register(username, password1, password2)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    updateLoginState(it, username,"注册成功")
                }

    }

    private fun updateLoginState(it: CookieBean, userName: String,msg:String) {

        if (it.errorCode < 0) {

            Toast.makeText(baseContext, it.errorMsg, Toast.LENGTH_SHORT).show()

        } else {

            SharePreference.saveKV(SharePreference.IS_LOGIN, true)

            SharePreference.saveKV(SharePreference.USER_NAME, userName)

            EventBus.getDefault().postSticky(EventFactory.LoginState(true, userName))

            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()

            go2MainActivity()

        }
    }

    private fun saveNamePwd(userName: String, userPwd: String) {
        if (isSaveNamePwd) {
            SharePreference.saveKV(SharePreference.USER_NAME, userName)
            SharePreference.saveKV(SharePreference.USER_PWD, userPwd)
        }
    }

    private fun checkNameAndPassWord(userName: String, userPwd: String): Boolean {

        if (userName.isEmpty() || userName.length < 6 || userName.length > 50) return false

        if (userPwd.isEmpty() || userPwd.length < 6 || userName.length > 50) return false

//        <!--密码6~50位且为数字、字母、-、_-->

//        最长不得超过7个汉字，或14个字节(数字，字母和下划线)正则表达式
//        ^[\u4e00-\u9fa5]{1,7}$|^[\dA-Za-z_]{1,14}$
        var r = """^[\dA-Za-z_]{6,50}$""".toRegex()

        var r2 = """^[\dA-Za-z_]{6,50}$""".toRegex()

        return r.matches(userPwd)

    }

    private fun go2MainActivity() {
        switch_state.showView(SwitchStateLayout.VIEW_LOADING)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }



}

