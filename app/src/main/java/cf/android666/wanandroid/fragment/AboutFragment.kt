package cf.android666.wanandroid.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cf.android666.wanandroid.R
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.utils.SharePreference
import kotlinx.android.synthetic.main.fragment_about.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import android.content.Intent
import cf.android666.wanandroid.activity.LoginActivity
import cf.android666.wanandroid.activity.SettingActivity
import kotlinx.android.synthetic.main.activity_login.view.*

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class AboutFragment : BaseFragment() {

    var mView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_about, container, false)

        mView = view

        refreashUi()

        setOnClick(view)

        return view
    }

    private fun setOnClick(view: View) {

        var userNameEt = view.user_name

//        var userPwdEt = view.user_password
//
//        var userRePwdEt = view.user_password_re
//
//        view.login_btn.setOnClickListener {
//
//            view.register_btn.setBackgroundColor(Color.TRANSPARENT)
//
//            view.login_btn.setBackgroundColor(resources.getColor(R.color.colorBottomNav))
//
//            userRePwdEt.visibility = View.GONE
//
//            val userName = userNameEt.text.toString()
//
//            val userPwd = userPwdEt.text.toString()
//
//            if (userName.isEmpty()  || userPwd.isEmpty()) {
//                Toast.makeText(context,"账号或密码为空",Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            login(userName,userPwd)
//
//        }

//        view.register_btn.setOnClickListener {
//
//            view.register_btn.setBackgroundColor(resources.getColor(R.color.colorBottomNav))
//
//            view.login_btn.setBackgroundColor(Color.TRANSPARENT)
//
//            if (view.user_password_re.visibility == View.VISIBLE) {
//
//                userRePwdEt.visibility = View.VISIBLE
//
//                register(userNameEt.text.toString(), userPwdEt.text.toString(), userRePwdEt.text.toString())
//
//            } else {
//                view.user_password_re.visibility = View.VISIBLE
//
//            }
//
//
//        }

//        view.update.setOnClickListener {
//

//
//        view.share.setOnClickListener {
//
//        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreashUi() {

        if (mView == null) {
            return
        }

        var isNightMode = SharePreference.getV<Boolean>(SharePreference.IS_NIGHT_MODE, false)

        mView!!.night_mode.isChecked = isNightMode

        mView!!.night_mode.setOnClickListener {

            SharePreference.saveKV(SharePreference.IS_NIGHT_MODE, mView!!.night_mode.isChecked)

            updateTheme()

        }

        mView!!.more_tv.setOnClickListener {
            context.startActivity(Intent(activity, SettingActivity::class.java))
        }

        var isLogin = SharePreference.getV<Boolean>(SharePreference.IS_LOGIN, false)

        var username = SharePreference.getV<String>(SharePreference.USER_NAME, "请点击头像登录")

        mView!!.about_user_name.text = username

        if (isLogin) {

            mView!!.user_icon.setOnClickListener {

                AlertDialog.Builder(context)
                        .setTitle("确定注销登录吗？")
                        .setMessage("注销后将无法收藏、查看已经收藏的文章，其他功能不受影响。" +
                                "您仍然可以再次登录查看这些信息")
                        .setPositiveButton("去意已决") { dialog, which ->

                            SharePreference.clear()

                            dialog.dismiss()

                            Toast.makeText(context, "注销成功", Toast.LENGTH_SHORT).show()

                        }.setNegativeButton("再玩会儿") {

                            dialog, which ->

                            dialog.dismiss()

                        }.create().show()

            }

//            mView!!.about_login_layout.visibility = View.GONE
//
//
//            mView!!.power_btn.text = "注销"
//
//            mView!!.power_btn.setOnClickListener {
//
//               Toast.makeText(context,"注销啦",Toast.LENGTH_SHORT).show()
//
//
//                mView!!.power_btn.text = "登录/注册"
//
//                SharePreference.clear()
//
//                isLogin = false
//
//                SharePreference.saveKV(SharePreference.IS_LOGIN,false)
//
//                SharePreference.saveKV(SharePreference.USER_NAME,"未登录")
//
//                MessageEvent.isLogin = false
//
//                EventBus.getDefault().post(MessageEvent)
//
//                refreashUi()
//            }

//            mView!!.fr_text.text = "收藏：" + SharePreference.getV(SharePreference.FAVORITE_COUNT,"")


        } else {
            mView!!.user_icon.setOnClickListener {

                context.startActivity(Intent(activity, LoginActivity::class.java))

            }
//
//            mView!!.power_btn.text = "登录/注册"
//
//            mView!!.power_btn.setOnClickListener {
//
//                mView!!.power_btn.setBackgroundColor(Color.TRANSPARENT)
//
//
//                mView!!.about_login_layout.visibility =
//                        if (mView!!.about_login_layout.visibility == View.VISIBLE) View.GONE
//                        else View.VISIBLE
//
//            }
//
//            mView!!.about_login_layout.visibility = View.VISIBLE
//
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
//

        }

    }

    private fun updateTheme() {

        //todo 在切换activity的时候做个动画掩饰，可以参考酷软

        val intent = activity.intent
        activity.overridePendingTransition(0, 0)//不设置进入退出动画
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        activity.finish()
        activity.overridePendingTransition(0, 0)
        startActivity(intent)
    }

}