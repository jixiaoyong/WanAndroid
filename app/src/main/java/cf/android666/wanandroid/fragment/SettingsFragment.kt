package cf.android666.wanandroid.fragment

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import cf.android666.wanandroid.R
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.base.toast
import cf.android666.wanandroid.utils.SharePreference
import cf.android666.wanandroid.utils.SuperUtil

/**
 * Created by jixiaoyong on 2018/3/12.
 * email:jixiaoyong1995@gmail.com
 */
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        addPreferencesFromResource(R.xml.settings)

        var version = requireContext().packageManager
                .getPackageInfo(requireContext().packageName, 0).versionName

        (preferenceScreen.getPreference(0) as PreferenceCategory).getPreference(1).summary = version

    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {

        when (preference!!.key) {
            "update" -> {
                if ((preference as SwitchPreferenceCompat).isChecked) {
                    SuperUtil.update(requireContext(), true)
                }
                SharePreference.saveKV(SharePreference.IS_AUTO_UPDATE, preference.isChecked)
            }
            "version" -> {
                toast("${getString(R.string.current_version)}${preference.summary}")
            }
            "project_url" -> {
                SuperUtil.startActivity(requireContext(), ContentActivity::class.java
                        , preference.summary.toString())
            }
            "author_url" -> {
                SuperUtil.startActivity(requireContext(), ContentActivity::class.java
                        , preference.summary.toString())
            }
            "about_summary" -> {
            }
        }


        return super.onPreferenceTreeClick(preference)
    }
}