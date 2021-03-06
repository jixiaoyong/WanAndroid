package io.github.jixiaoyong.wanandroid.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cf.android666.applibrary.Logger
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam
import io.github.jixiaoyong.wanandroid.api.bean.DataPageOf
import io.github.jixiaoyong.wanandroid.api.bean.RemoteDataBean
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * description ： TODO
 * @author : jixiaoyong
 * @email : jixiaoyong1995@gmail.com
 * @date : 2021/3/10
 */
class IndexPostPagingSource(private val netWorkRepository: NetWorkRepository) : PagingSource<Int, DataIndexPostParam>() {

    override fun getRefreshKey(state: PagingState<Int, DataIndexPostParam>): Int? {
        return null
    }

    /**
     * load必须在异步执行耗时操作
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataIndexPostParam> {
        val currentPage = params.key ?: 0
        try {
            val result = withContext(Dispatchers.IO) {
                netWorkRepository.getIndexPostOnPage(currentPage).data?.datas ?: arrayListOf()
            }
            val nextKey = if (result.isNullOrEmpty()) {
                null
            } else {
                currentPage + (params.loadSize / CommonConstants.Paging.PAGE_SIZE)
            }
            Logger.d("result  (currentPage$currentPage)" + result.joinToString())
            return LoadResult.Page(
                data = result,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            Logger.e(e)
            return LoadResult.Error(e)
        }
    }
}

class PostPagingSource<T : Any>(
    private val netFunc: suspend (page: Int) -> RemoteDataBean<DataPageOf<T>>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val currentPage = params.key ?: 0
        val result = try {
            withContext(Dispatchers.IO) {
                netFunc(currentPage).data?.datas ?: arrayListOf()
            }
        } catch (e: Exception) {
            Logger.e(e)
            return LoadResult.Error(e)
        }
        val nextKey = if (result.isNullOrEmpty()) {
            null
        } else {
            currentPage + (params.loadSize / CommonConstants.Paging.PAGE_SIZE)
        }
        Logger.d("result (currentPage$currentPage)" + result.joinToString())
        return LoadResult.Page(
            data = result,
            prevKey = if (currentPage == 0) null else currentPage - 1,
            nextKey = nextKey
        )
    }
}
