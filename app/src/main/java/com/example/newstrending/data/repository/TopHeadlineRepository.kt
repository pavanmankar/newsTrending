package com.example.newstrending.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.newstrending.data.api.NetworkService
import com.example.newstrending.data.model.Article
import com.example.newstrending.ui.base.PaggingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.Dispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlines(country: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles
        }.flowOn(Dispatchers.Default)
    }

    fun loadData(country : String) : Flow<List<Article>> {
        return flow<List<Article>> {
            Pager(
                config = PagingConfig(pageSize = 10, maxSize = 100),
                pagingSourceFactory = { PaggingSource(networkService,country) }
            )
        }
    }

    fun getQuotes(country: String) = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 100),
        pagingSourceFactory = { PaggingSource(networkService,country) }
    ).flow

}