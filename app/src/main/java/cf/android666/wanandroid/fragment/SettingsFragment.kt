package cf.android666.wanandroid.fragment

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import cf.android666.wanandroid.R

/**
 * Created by jixiaoyong on 2018/3/12.
 * email:jixiaoyong1995@gmail.com
 */
class SettingsFragment:PreferenceFragmentCompat(){
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
    }

}