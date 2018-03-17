package cf.android666.wanandroid.fragment

import android.os.Bundle
import android.support.v7.preference.*
import android.widget.Switch
import cf.android666.wanandroid.R
import cf.android666.wanandroid.activity.ContentActivity
import cf.android666.wanandroid.utils.SharePreference
import cf.android666.wanandroid.utils.SuperUtil

/**
 * Created by jixiaoyong on 2018/3/12.
 * email:jixiaoyong1995@gmail.com
 */
class SettingsFragment:PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        addPreferencesFromResource(R.xml.settings)

        var version = context.packageManager.getPackageInfo(context.packageName,0).versionName

        (preferenceScreen.getPreference(0) as PreferenceCategory).getPreference(1).summary = version

}

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {

        when(preference!!.key) {
            "update"-> {

                if ((preference as SwitchPreferenceCompat).isChecked) {
                    SuperUtil.update(context,true)

                }

                SharePreference.saveKV(SharePreference.IS_AUTO_UPDATE,preference.isChecked)
            }

            "version"-> {SuperUtil.toast(context,
                    "当前版本是${preference.summary}")}

            "project_url"-> {SuperUtil.startActivity(context,ContentActivity::class.java
                    ,preference.summary.toString())}

            "author_url"-> {SuperUtil.startActivity(context,ContentActivity::class.java
                    ,preference.summary.toString())}
            "about_summary"-> {}
        }


        return super.onPreferenceTreeClick(preference)
    }
}