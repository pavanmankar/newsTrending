package com.example.newstrending.data.repository

import com.example.newstrending.data.api.NetworkService
import com.example.newstrending.data.database.entity.articleToDbArticle
import com.example.newstrending.data.database.entity.dbArticleToArticle
import com.example.newstrending.data.database.service.TopHeadline.TopHeadlineDbService
import com.example.newstrending.data.model.Article
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService,
    private val topHeadlineDbService: TopHeadlineDbService
) {

    @OptIn(FlowPreview::class)
    fun getTopHeadlines(country: String, query: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlines(country, query))
        }.map {
            it.articles
        }.flatMapConcat {
            flow {
                topHeadlineDbService.deleteAllArticles()
                topHeadlineDbService.insertTopHeadlines(it.articleToDbArticle())
                emit(Unit)
            }
        }.flatMapConcat {
            topHeadlineDbService.getArticles()
        }.map {
            it.dbArticleToArticle()
        }
    }

    fun getTopHeadlinesFromDb(): Flow<List<Article>> {
        return topHeadlineDbService.getArticles().map { it.dbArticleToArticle() }
    }

}