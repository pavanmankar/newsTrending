package com.example.newstrending.ui.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newstrending.data.api.NetworkService
import com.example.newstrending.data.model.Article
import java.lang.Exception

class PaggingSource(private val networkService: NetworkService, private val country: String) :
    PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val position = params.key ?: 1
            val response = networkService.getTopHeadlines(country,"")
            val page = response.totalResults % 10
            var pageCount = 0
            if (page > 0) {
                pageCount = (response.totalResults / 10) + 1
            } else {
                pageCount = response.totalResults / 10
            }

            return LoadResult.Page(
                data = response.articles,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == pageCount) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}