package cf.android666.wanandroid.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cf.android666.wanandroid.R
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.cookie.CookieTools
import cf.android666.wanandroid.utils.MessageEvent
import cf.android666.wanandroid.utils.SharePreference
import kotlinx.android.synthetic.main.fragment_about.view.*
import kotlinx.android.synthetic.main.fragment_about_login.view.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_about_settings.view.*
import org.greenrobot.eventbus.EventBus


/**
 * Created by jixiaoyong on 2018/2/25.
 */
class AboutFragment : BaseFragment() {

    var mView:View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_about, container, false)

        mView = view

        refreashUi()

        view.power_btn.setOnClickListener {

            var isLogin = SharePreference.getV<Boolean>(SharePreference.IS_LOGIN,false)

            if (isLogin) {

                Toast.makeText(context, "注销啦", Toast.LENGTH_SHORT).show()

                SharePreference.saveKV(SharePreference.IS_LOGIN, false)

                var msg = MessageEvent()

                msg.isLogin = false

                EventBus.getDefault().post(msg)

            } else {

                var userNameEt = view.user_name

                var userPwdEt = view.user_password

                var userRePwdEt = view.user_password_re

                view.login_btn.setOnClickListener {

                    view.register_btn.setBackgroundColor(Color.TRANSPARENT)

                    view.login_btn.setBackgroundColor(resources.getColor(R.color.colorBottomNav))

                    userRePwdEt.visibility = View.GONE

                    val userName = userNameEt.text.toString()

                    val userPwd = userPwdEt.text.toString()

                    checkNameAndPassWord(userName, userPwd)

                    login(userName,userPwd)

                }

                view.register_btn.setOnClickListener {

                    view.register_btn.setBackgroundColor(resources.getColor(R.color.colorBottomNav))

                    view.login_btn.setBackgroundColor(Color.TRANSPARENT)

                    userRePwdEt.visibility = View.VISIBLE

                    register(userNameEt.text.toString(),userPwdEt.text.toString(),userRePwdEt.text.toString())

                }
            }
        }

        return view
    }

    private fun register(username: String, password1: String, password2: String) {

        Toast.makeText(context,"注册~~",Toast.LENGTH_SHORT).show()

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
                    if (it.errorCode < 0) {

                        Toast.makeText(context, it.errorMsg, Toast.LENGTH_SHORT).show()

                    } else{

                        var event = MessageEvent()

                        event.isLogin = true

                        SharePreference.saveKV(SharePreference.IS_LOGIN, true)

                        EventBus.getDefault().post(event)

                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()


                        refreashUi()
                    }
                }

    }

    fun refreashUi(){

        if (mView == null) {
            return
        }

        var isLogin = SharePreference.getV<Boolean>(SharePreference.IS_LOGIN, false)

        if (isLogin) {

            mView!!.power_btn.text = "注销"

            mView!!.power_btn.tag = true

            mView!!.settings_layout.inflate()

            mView!!.fr_text.text = "收藏：" + SharePreference.getV(SharePreference.FAVORITE_COUNT,"")

//            if (mView!!.login_layout != null) {
//
//                mView!!.login_layout.inflate()//此后view stub被删除
//
//                mView!!.power_btn.setBackgroundColor(Color.TRANSPARENT)
//
//                mView!!.register_btn.setBackgroundColor(Color.TRANSPARENT)
//
//                mView!!.login_btn.setBackgroundColor(resources.getColor(R.color.colorBottomNav))
//
//            } else {


//                if (mView!!.about_login_layout.visibility == View.VISIBLE) {
//
//                    mView!!.about_login_layout.visibility = View.GONE
//
//                    mView!!.power_btn.setBackgroundColor(resources.getColor(R.color.colorBottomNav))
//
//                    mView!!.power_layout.setBackgroundColor(Color.TRANSPARENT)
//
//                    mView!!.arrow_img.background = resources.getDrawable(R.drawable.arrow_down)
//                }
//                 else {
//
//                    mView!!.about_login_layout.visibility = View.VISIBLE
//
//                    mView!!.power_btn.setBackgroundColor(Color.TRANSPARENT)
//
//                    mView!!.power_layout.background = resources.getDrawable(R.drawable.bg_border)
//
//                    mView!!.arrow_img.background = resources.getDrawable(R.drawable.arrow_up)
//
//                }
//            }


        } else {
            mView!!.power_btn.text = "登录/注册"

        }

    }

}