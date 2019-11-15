package io.github.jixiaoyong.wanandroid.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: todo
 */
@Dao
interface CookiesDao {

    @Query("SELECT * FROM cookies")
    fun queryAllCookies(): List<CookiesBean>

    @Query("SELECT * FROM cookies")
    fun queryAllCookiesAsync(): LiveData<List<CookiesBean>>

    @Insert
    fun insert(vararg cookiesBean: CookiesBean)

    @Query("DELETE FROM cookies")
    fun cleanAll()
}