package cf.android666.wanandroid.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cf.android666.wanandroid.R
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.cookie.CookieTools
import cf.android666.wanandroid.cookie.Preference
import cf.android666.wanandroid.utils.MessageEvent
import cf.android666.wanandroid.utils.SharePreference
import kotlinx.android.synthetic.main.fragment_about.view.*
import kotlinx.android.synthetic.main.fragment_about_login.view.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus


/**
 * Created by jixiaoyong on 2018/2/25.
 */
class AboutFragment : BaseFragment() {



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_about, container, false)


        var isLogin = false

//        var isLogin = SharePreference.getInstance().getBoolean(SharePreference.IS_LOGIN, false)

        view.power_btn.text = if (isLogin) "注销" else "登录/注册"

        if (isLogin) {
            view.settings_layout.inflate()
        }

        view.power_btn.setOnClickListener {

            if (isLogin) {

                Toast.makeText(context, "注销啦", Toast.LENGTH_SHORT).show()

                isLogin = false

//                SharePreference.saveKV(SharePreference.IS_LOGIN, false)


            } else {


                if (view.login_layout != null) {

                    view.login_layout.inflate()//此后view stub被删除

                    view.power_btn.setBackgroundColor(Color.TRANSPARENT)

                    view.register_btn.setBackgroundColor(Color.TRANSPARENT)

                    view.login_btn.setBackgroundColor(resources.getColor(R.color.colorBottomNav))

                } else {

                    if (view.about_login_layout.visibility == View.VISIBLE) {

                        view.about_login_layout.visibility = View.GONE

                        view.power_btn.setBackgroundColor(resources.getColor(R.color.colorBottomNav))

                        view.power_layout.setBackgroundColor(Color.TRANSPARENT)

                        view.arrow_img.background = resources.getDrawable(R.drawable.arrow_down)

                    } else {

                        view.about_login_layout.visibility = View.VISIBLE

                        view.power_btn.setBackgroundColor(Color.TRANSPARENT)

                        view.power_layout.background = resources.getDrawable(R.drawable.bg_border)

                        view.arrow_img.background = resources.getDrawable(R.drawable.arrow_up)

                    }
                }


                var userNameEt = view.user_name

                var userPwdEt = view.user_password

                var userRePwdEt = view.user_password_re


                view.login_btn.setOnClickListener {

                    view.register_btn.setBackgroundColor(Color.TRANSPARENT)

                    view.login_btn.setBackgroundColor(resources.getColor(R.color.colorBottomNav))

                    userRePwdEt.visibility = View.GONE

                    val userName = userNameEt.text.toString()

                    val userPwd = userPwdEt.text.toString()

                    var  isPass = checkNameAndPassWord(userName, userPwd)

                    if (isPass) {

                        Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show()
                        isLogin = true

                    } else {
                        Toast.makeText(context,"账号或密码错误，请重试",Toast.LENGTH_SHORT).show()
                        isLogin = false
                    }

//                   SharePreference.saveKV(SharePreference.IS_LOGIN,isLogin)

                    login(userName,userPwd)

                }

                view.register_btn.setOnClickListener {

                    view.register_btn.setBackgroundColor(resources.getColor(R.color.colorBottomNav))

                    view.login_btn.setBackgroundColor(Color.TRANSPARENT)

                    userRePwdEt.visibility = View.VISIBLE


                }
            }
        }

        return view
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

    private fun login(userName: String,userPwd: String) {

        CookieTools.getCookieService()!!
                .login(userName,userPwd)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it != null) {

                        Toast.makeText(context,"login success!",Toast.LENGTH_SHORT).show()

                        refreashUi()

                        var event = MessageEvent()

                        event.isLogin = true

//                        SharePreference.saveKV(SharePreference.IS_LOGIN,true)

                        EventBus.getDefault().post(event)
                    }
                }

    }

    private fun refreashUi() {



    }


}