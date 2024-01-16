package com.example.newstrending.data.database.entity

import androidx.room.*
import com.example.newstrending.data.model.Source


@Entity(
    tableName = "articles",
    indices = [Index(
        value = ["title", "id"],
        unique = true
    )]
)
data class DbArticle(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int = 0,
    @ColumnInfo("title")
    val title: String? = "",
    @ColumnInfo("description")
    val description: String? = "",
    @ColumnInfo("url")
    val url: String? = "",
    @ColumnInfo("urlToImage")
    val imageUrl: String? = "",

    @Embedded
    val source: DbSource
)

