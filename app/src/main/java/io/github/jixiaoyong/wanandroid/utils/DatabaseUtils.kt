package io.github.jixiaoyong.wanandroid.utils

import android.content.Context
import androidx.room.Room
import io.github.jixiaoyong.wanandroid.data.AppDatabase

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: todo
 */
object DatabaseUtils {

    lateinit var database: AppDatabase

    fun initDatabase(applicationContext: Context) {
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "wa_database")
                .build()
    }
}