package com.example.newstrending.data.database.service.TopHeadline

import com.example.newstrending.data.database.entity.DbArticle
import com.example.newstrending.data.database.entity.dbArticleToArticle
import com.example.newstrending.data.database.service.NewsDatabase
import com.example.newstrending.data.model.Article
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopHeadlineDbServiceImpl @Inject constructor(private val newsDatabase: NewsDatabase) : TopHeadlineDbService {

    override suspend fun insertTopHeadlines(articles: List<DbArticle>) {
        newsDatabase.articleDao().insertAll(articles)
    }

    @OptIn(FlowPreview::class)
    override suspend fun getArticles(): Flow<List<DbArticle>> {
        return newsDatabase.articleDao().getAllArticles()

    }

    override suspend fun deleteAllArticles() {
        newsDatabase.articleDao().deleteAll()
    }
}