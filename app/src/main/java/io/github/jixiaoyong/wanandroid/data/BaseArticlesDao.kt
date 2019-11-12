package io.github.jixiaoyong.wanandroid.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import io.github.jixiaoyong.wanandroid.api.bean.BaseArticlesBean

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: todo
 */
@Dao
interface BaseArticlesDao {

    @Query("SELECT * FROM baseArticles")
    fun queryAllArticles(): LiveData<List<BaseArticlesBean>>

}