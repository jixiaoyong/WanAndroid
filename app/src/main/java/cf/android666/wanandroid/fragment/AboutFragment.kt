package cf.android666.wanandroid.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import cf.android666.wanandroid.R
import cf.android666.wanandroid.`interface`.RefreshUiInterface
import cf.android666.wanandroid.activity.LoginActivity
import cf.android666.wanandroid.activity.SettingActivity
import cf.android666.wanandroid.base.BaseFragment
import cf.android666.wanandroid.utils.EventFactory
import cf.android666.wanandroid.utils.EventInterface
import cf.android666.wanandroid.utils.SharePreference
import kotlinx.android.synthetic.main.fragment_about.view.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by jixiaoyong on 2018/2/25.
 */
class AboutFragment : BaseFragment(), RefreshUiInterface {


    override fun lazyLoadData() {
        if (mView == null) {
            return
        }

        val isNightMode = SharePreference.getV<Boolean>(SharePreference.IS_NIGHT_MODE, false)
        mView!!.night_mode.isChecked = isNightMode
        val isLogin = SharePreference.getV<Boolean>(SharePreference.IS_LOGIN, false)
        updateUserName()

        if (isLogin) {
            mView!!.user_icon.setOnClickListener {
                showDialog()
            }
        } else {
            mView!!.user_icon.setOnClickListener {
                requireContext().startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
    }

    override fun onCreateViewState(savedInstanceState: Bundle?) {
        mView!!.night_mode.setOnClickListener {
            SharePreference.saveKV(SharePreference.IS_NIGHT_MODE, mView!!.night_mode.isChecked)
            updateTheme()
        }

        mView!!.more_tv.setOnClickListener {
            requireContext().startActivity(Intent(activity, SettingActivity::class.java))
        }
    }


    override var layoutId = R.layout.fragment_about

    override fun refreshUi(eventInterface: EventInterface) {
        when (eventInterface) {
            is EventFactory.LoginState -> {
                updateUserName()
            }
        }
        lazyLoadData()
    }

    private fun showDialog() {
        AlertDialog.Builder(context)
                .setTitle(getString(R.string.title_sure_to_unlogin))
                .setMessage(getString(R.string.unlogin_tips))
                .setPositiveButton(getString(R.string.sure_to_leave)) { dialog, which ->
                    SharePreference.clear()
                    dialog.dismiss()
                    EventBus.getDefault().postSticky(EventFactory.LoginState(false, ""))
                    Toast.makeText(context, getString(R.string.unlogin_succeeded), Toast.LENGTH_SHORT).show()
                }.setNegativeButton(getString(R.string.wait_a_moment)) { dialog, _ ->
                    dialog.dismiss()
                }.create().show()
    }

    private fun updateTheme() {
        //todo 在切换activity的时候做个动画掩饰，可以参考酷软
        val intent = requireActivity().intent
        requireActivity().overridePendingTransition(0, 0)//不设置进入退出动画
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        requireActivity().finish()
        requireActivity().overridePendingTransition(0, 0)
        startActivity(intent)
    }

    fun updateUserName() {
        val username = SharePreference.getV<String>(SharePreference.USER_NAME, getString(R.string.click_icon_to_login))
        mView?.about_user_name?.text = if (username.isEmpty()) getString(R.string.click_icon_to_login) else username
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