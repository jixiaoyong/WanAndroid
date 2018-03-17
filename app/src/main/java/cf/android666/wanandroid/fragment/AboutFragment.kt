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
import android.content.Intent
import cf.android666.wanandroid.`interface`.RefreshUiInterface
import cf.android666.wanandroid.activity.LoginActivity
import cf.android666.wanandroid.activity.SettingActivity
import cf.android666.wanandroid.utils.EventFactory
import cf.android666.wanandroid.utils.EventInterface
import org.greenrobot.eventbus.EventBus

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class AboutFragment : BaseFragment() ,RefreshUiInterface{


    override fun lazyLoadData() {
        if (mView == null) {
            return
        }

        val isNightMode = SharePreference.getV<Boolean>(SharePreference.IS_NIGHT_MODE, false)

        mView!!.night_mode.isChecked = isNightMode

        var isLogin = SharePreference.getV<Boolean>(SharePreference.IS_LOGIN, false)

        updateUserName()

        if (isLogin) {

            mView!!.user_icon.setOnClickListener {

                showDialog()

            }

        } else {
            mView!!.user_icon.setOnClickListener {

                context.startActivity(Intent(activity, LoginActivity::class.java))

            }

        }
    }

    override fun onCreateViewState(savedInstanceState: Bundle?) {

        mView!!.night_mode.setOnClickListener {

            SharePreference.saveKV(SharePreference.IS_NIGHT_MODE, mView!!.night_mode.isChecked)

            updateTheme()

        }

        mView!!.more_tv.setOnClickListener {
            context.startActivity(Intent(activity, SettingActivity::class.java))
        }

    }


    override var layoutId = R.layout.fragment_about

    override fun refreshUi(eventInterface: EventInterface) {

        when (eventInterface) {

            is EventFactory.LoginState ->{

                updateUserName()

            }
        }

        lazyLoadData()

    }

    private fun showDialog() {
        AlertDialog.Builder(context)
                .setTitle("确定注销登录吗？")
                .setMessage("注销后将无法收藏、查看已经收藏的文章，其他功能不受影响。" +
                        "您仍然可以再次登录查看这些信息")
                .setPositiveButton("去意已决") { dialog, which ->

                    SharePreference.clear()

                    dialog.dismiss()

                    EventBus.getDefault().post(EventFactory.NightMode(true))

                    Toast.makeText(context, "注销成功", Toast.LENGTH_SHORT).show()


                }.setNegativeButton("再玩会儿") {

                    dialog, which ->

                    dialog.dismiss()

                }.create().show()
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

    fun updateUserName() {

        var username = SharePreference.getV<String>(SharePreference.USER_NAME, "请点击头像登录")

        mView?.about_user_name?.text = if (username.isEmpty()) "请点击头像登录" else username
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)

    }

}