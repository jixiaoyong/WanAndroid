package cf.android666.wanandroid.utils

import android.content.Context
import android.content.SharedPreferences
import cf.android666.wanandroid.api.cookie.Preference


/**
 * Created by jixiaoyong on 2018/3/5.
 * email:jixiaoyong1995@gmail.com
 */

object SharePreference {

    val IS_LOGIN = "is_login"

    val IS_NIGHT_MODE = "is_night_mode"

    val FAVORITE_COUNT = "favorite_count"

    val USER_NAME = "user_name"

    var sharePreference: SharedPreferences? = null

    fun setContext(context: Context) {

        sharePreference = context.getSharedPreferences("setdddtings", Context.MODE_PRIVATE)

    }

    fun saveKV(key: String, value: Any): Boolean {

        val edit = sharePreference!!.edit()

        when (value) {

            is String -> edit.putString(key, value)

            is Int -> edit.putInt(key, value)

            is Boolean -> edit.putBoolean(key, value)

            else -> return false
        }

        edit.commit()

        return true

    }

     fun <T> getV(key: String ,value: Any) :T{

        val result = when (value) {

            is String -> sharePreference!!.getString(key,"")

            is Int -> sharePreference!!.getInt(key,0)

            is Boolean -> sharePreference!!.getBoolean(key,false)

            else -> throw Exception("not support type of class!")
        }

        return result as T
    }

    fun clear(){
        sharePreference!!.edit().clear().apply()
        Preference.clear()
    }


}