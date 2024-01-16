package com.example.newstrending.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newstrending.data.database.entity.DbArticle
import com.example.newstrending.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<DbArticle>)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<DbArticle>>

    @Query("DELETE FROM articles")
    fun deleteAll()

}