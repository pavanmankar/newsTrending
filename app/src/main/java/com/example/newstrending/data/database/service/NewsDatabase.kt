package com.example.newstrending.data.database.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newstrending.data.database.dao.ArticleDao
import com.example.newstrending.data.database.entity.DbArticle
import com.example.newstrending.data.model.Article
import javax.inject.Singleton


@Database(entities = [DbArticle::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}