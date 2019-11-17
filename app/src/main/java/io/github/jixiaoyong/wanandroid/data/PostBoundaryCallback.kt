package io.github.jixiaoyong.wanandroid.data

import androidx.annotation.MainThread
import androidx.paging.PagedList
import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-16
 * description: Paging分页加载库 加载数据
 */

class PostBoundaryCallback(private val loadFromWebToDb: (Int) -> Unit)
    : PagedList.BoundaryCallback<DataIndexPostParam>() {

    private var currentPage = 0

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        Logger.e("load zero data $currentPage")
        currentPage = 0
        loadFromWebToDb(currentPage)
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: DataIndexPostParam) {
        Logger.e("load more data $currentPage")
        currentPage++
        loadFromWebToDb(currentPage)
    }

    override fun onItemAtFrontLoaded(itemAtFront: DataIndexPostParam) {
        // ignored, since we only ever append to what's in the DB
    }

}
