package io.github.jixiaoyong.wanandroid.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.jixiaoyong.wanandroid.api.bean.DataIndexPostParam
import io.github.jixiaoyong.wanandroid.api.bean.DataPageOf
import io.github.jixiaoyong.wanandroid.api.bean.RemoteDataBean
import io.github.jixiaoyong.wanandroid.utils.CommonConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * description ： TODO
 * @author : shayn
 * @email : shayn@yeahka.com
 * @date : 2021/3/10
 */
class IndexPostPagingSource(private val netWorkRepository: NetWorkRepository) : PagingSource<Int, DataIndexPostParam>() {

    override fun getRefreshKey(state: PagingState<Int, DataIndexPostParam>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    /**
     * load必须在异步执行耗时操作
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataIndexPostParam> {
        val currentPage = params.key ?: 0
        val result = withContext(Dispatchers.IO) {
            netWorkRepository.getIndexPostOnPage(currentPage).data?.datas ?: arrayListOf()
        }
        val nextKey = if (result.isNullOrEmpty()) {
            null
        } else {
            currentPage + (params.loadSize / CommonConstants.Paging.PAGE_SIZE)
        }
        return LoadResult.Page(
            data = result,
            prevKey = if (currentPage == 0) null else currentPage - 1,
            nextKey = nextKey
        )
    }
}

class PostPagingSource<T : Any>(
    private val netFunc: (page: Int) -> RemoteDataBean<DataPageOf<T>>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val currentPage = params.key ?: 0
        val result = withContext(Dispatchers.IO) {
            netFunc(currentPage).data?.datas ?: arrayListOf()
        }
        val nextKey = if (result.isNullOrEmpty()) {
            null
        } else {
            currentPage + (params.loadSize / CommonConstants.Paging.PAGE_SIZE)
        }
        return LoadResult.Page(
            data = result,
            prevKey = if (currentPage == 0) null else currentPage - 1,
            nextKey = nextKey
        )
    }
}
