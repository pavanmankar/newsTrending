package com.example.newstrending.data.database.service.TopHeadline

import com.example.newstrending.data.database.entity.DbArticle
import com.example.newstrending.data.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface TopHeadlineDbService {

     fun insertTopHeadlines(articles: List<DbArticle>)
     fun getArticles(): Flow<List<DbArticle>>
     fun deleteAllArticles()

}