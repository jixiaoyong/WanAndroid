package io.github.jixiaoyong.wanandroid.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-23
 * description: todo
 */
@Entity(tableName = "preference")
data class Preference(
        var key: String,
        var value: String,
        @PrimaryKey(autoGenerate = true)
        val _id: Int? = null
)