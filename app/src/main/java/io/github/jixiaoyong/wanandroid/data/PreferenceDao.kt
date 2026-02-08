package io.github.jixiaoyong.wanandroid.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: todo
 */
@Dao
interface PreferenceDao {

    @Query("SELECT * FROM preference WHERE `key` == :key")
    fun queryPreferenceByKey(key: String): LiveData<Preference?>

    @Query("SELECT * FROM preference WHERE `key` == :key")
    fun queryPreferenceByKeySync(key: String): Preference?

    @Query("DELETE  FROM preference")
    fun deleteAllArticles()

    @Insert
    fun insert(vararg preference: Preference)

    @Update
    fun update(vararg preference: Preference)
}
