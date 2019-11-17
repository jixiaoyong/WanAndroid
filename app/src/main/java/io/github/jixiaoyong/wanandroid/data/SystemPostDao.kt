package io.github.jixiaoyong.wanandroid.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: todo
 */
@Dao
interface SystemPostDao {

    @Query("SELECT * FROM baseArticles")
    fun queryAllArticles(): DataSource.Factory<Int, DataIndexPostParam>

    @Query("DELETE  FROM baseArticles")
    fun deleteAllArticles()

    @Insert
    fun insert(baseArticlesBeans: List<DataIndexPostParam>)


    @Update
    fun update(vararg baseArticlesBeans: DataIndexPostParam)
}