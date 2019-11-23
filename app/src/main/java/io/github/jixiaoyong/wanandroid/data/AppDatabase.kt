package io.github.jixiaoyong.wanandroid.data

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: todo
 */
@Database(entities = [DataIndexPostParam::class, CookiesBean::class, Preference::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun baseArticlesDao(): BaseArticlesDao
    abstract fun cookiesDao(): CookiesDao
    abstract fun preferenceDao(): PreferenceDao

}