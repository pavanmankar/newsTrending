package com.example.newstrending.data.database.service.TopHeadline

import com.example.newstrending.data.database.entity.DbArticle
import com.example.newstrending.data.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface TopHeadlineDbService {

    suspend fun insertTopHeadlines(articles: List<DbArticle>)
    suspend fun getArticles(): Flow<List<DbArticle>>
    suspend fun deleteAllArticles()

}