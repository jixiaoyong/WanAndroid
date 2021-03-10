package io.github.jixiaoyong.wanandroid.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.R
import io.github.jixiaoyong.wanandroid.api.bean.CookieBean
import io.github.jixiaoyong.wanandroid.base.BaseActivity
import io.github.jixiaoyong.wanandroid.base.toast
import io.github.jixiaoyong.wanandroid.databinding.ActivityLoginRegisterBinding
import io.github.jixiaoyong.wanandroid.utils.InjectUtils
import io.github.jixiaoyong.wanandroid.utils.NetUtils
import io.github.jixiaoyong.wanandroid.utils.Utils
import io.github.jixiaoyong.wanandroid.view.ProgressDialog
import io.github.jixiaoyong.wanandroid.viewmodel.LoginAndRegisterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-05
 * description: 登录、注册的页面
 */
class LoginRegisterActivity : BaseActivity() {

    private val viewModel: LoginAndRegisterViewModel by viewModels { InjectUtils.provideLoginRegisterViewModelFactory() }
    private lateinit var dataBinding: ActivityLoginRegisterBinding

    private var progressView: ProgressDialog? = null

    private var showPwd = false
    private var showRePwd = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_register)

        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this

        initView()
    }

    private fun initView() {
        progressView = ProgressDialog(this)

        dataBinding.changeStateBtn.setOnClickListener {
            val isLoginPage = viewModel.isLogin.value ?: true
            viewModel.isLogin.value = !isLoginPage
        }

        dataBinding.skipBtn.setOnClickListener {
            startActivity(Intent(this@LoginRegisterActivity, MainActivity::class.java))
            finish()
        }

        dataBinding.inputGroup.userNameEditText.doOnTextChanged { text, start, count, after ->
            viewModel.nameInputLength.value = text?.length ?: 0
        }

        dataBinding.inputGroup.pwdEditText.doOnTextChanged { text, start, count, after ->
            viewModel.pwdInputLength.value = text?.length ?: 0
        }

        dataBinding.inputGroup.pwdReEditText.doOnTextChanged { text, start, count, after ->
            viewModel.repwdInputLength.value = text?.length ?: 0
        }

        dataBinding.inputGroup.loginRegisterBtn.setOnClickListener {
            val userName = dataBinding.inputGroup.userNameEditText.text.toString()
            val userPwd = dataBinding.inputGroup.pwdEditText.text.toString()
            val userRePwd = dataBinding.inputGroup.pwdReEditText.text.toString()
            if (viewModel.isLogin.value != false) {
                dealLogin(userName, userPwd)
            } else {
                dealRegister(userName, userPwd, userRePwd)
            }
        }

        dataBinding.inputGroup.pwdIcon.setOnClickListener {
            dataBinding.inputGroup.pwdEditText.transformationMethod = if (showPwd) {
                dataBinding.inputGroup.pwdIcon.setBackgroundResource(R.drawable.ic_eye_open)
                HideReturnsTransformationMethod.getInstance()
            } else {
                dataBinding.inputGroup.pwdIcon.setBackgroundResource(R.drawable.ic_eye_close)
                PasswordTransformationMethod.getInstance()
            }
            showPwd = !showPwd
        }

        dataBinding.inputGroup.pwdReIcon.setOnClickListener {
            dataBinding.inputGroup.pwdReEditText.transformationMethod = if (showRePwd) {
                dataBinding.inputGroup.pwdReIcon.setBackgroundResource(R.drawable.ic_eye_open)
                HideReturnsTransformationMethod.getInstance()
            } else {
                dataBinding.inputGroup.pwdReIcon.setBackgroundResource(R.drawable.ic_eye_close)
                PasswordTransformationMethod.getInstance()
            }
            showRePwd = !showRePwd
        }
    }

    private fun dealRegister(userName: String, userPwd: String, userRePwd: String) {
        if (!Utils.isNameCorrect(userName)) {
            toast(getString(R.string.tips_name_not_correct))
            return
        }

        if (!Utils.isNameCorrect(userPwd)) {
            toast(getString(R.string.tips_pwd_not_correct))
            return
        }

        if (!Utils.isNameCorrect(userRePwd)) {
            toast(getString(R.string.tips_pwd_not_correct))
            return
        }

        if (userPwd != userRePwd) {
            toast(getString(R.string.tips_repwd_not_match))
            return
        }

        viewModel.register(userName, userPwd, userRePwd)
    }

    private fun dealLogin(userName: String, userPwd: String) {
        if (!Utils.isNameCorrect(userName)) {
            toast(getString(R.string.tips_name_not_correct))
            return
        }

        if (!Utils.isNameCorrect(userPwd)) {
            toast(getString(R.string.tips_pwd_not_correct))
            return
        }

        lifecycleScope.launch {
            showProgress()
            try {
                val result = withContext(Dispatchers.IO) {
                    Logger.d("开始联网尝试登录")
                    viewModel.login(userName, userPwd)
                }
                if (result.errorCode == NetUtils.ErrorCode.SUCCEEDED) {
                    toast(getString(R.string.tips_login_succeeded))
                    onLoginRegisterSucceeded()
                } else {
                    dealWithErrorCode(result)
                }
            } catch (e: Exception) {
                toast(getString(R.string.tips_network_error))
            }
            dismissProgress()
        }
    }

    private fun onLoginRegisterSucceeded() {
        startActivity(Intent(this@LoginRegisterActivity, MainActivity::class.java))
        finish()
    }

    private fun showProgress(text: String? = null) {
        progressView?.dismiss()
        if (progressView == null) {
            progressView = ProgressDialog(this, text)
        } else {
            progressView?.setProgressText(text)
        }
        progressView?.show()
    }

    private fun dismissProgress() {
        progressView?.dismiss()
    }

    private fun dealWithErrorCode(result: CookieBean) {
        Logger.e("error code:${result.errorCode}")
    }
}
