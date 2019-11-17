package io.github.jixiaoyong.wanandroid.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.jixiaoyong.wanandroid.api.ApiCommondConstants
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: todo
 */
@Dao
interface BaseArticlesDao {
//
//    @Query("SELECT * FROM baseArticles WHERE _postType == ${ApiCommondConstants.PostType.IndexPost}")
//    fun queryAllArticles(): DataSource.Factory<Int, DataIndexPostParam>


    @Query("SELECT * FROM baseArticles WHERE _postType == :postType")
    fun queryAllArticles(postType: Int)
            : DataSource.Factory<Int, DataIndexPostParam>

    @Query("DELETE  FROM baseArticles WHERE _postType == :postType")
    fun deleteAllArticles(postType: Int = ApiCommondConstants.PostType.IndexPost)

    @Insert
    fun insert(baseArticlesBeans: List<DataIndexPostParam>)


    @Update
    fun update(vararg baseArticlesBeans: DataIndexPostParam)
}