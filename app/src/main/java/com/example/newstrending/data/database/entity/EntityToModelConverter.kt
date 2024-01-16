package com.example.newstrending.data.database.entity

import com.example.newstrending.data.model.Article
import com.example.newstrending.data.model.Source

fun List<DbArticle>.dbArticleToArticle(): List<Article> {
    return map {
        Article(
            title = it.title,
            description = it.description,
            url = it.url,
            imageUrl = it.imageUrl,
            source = it.source.dbSourceEntityToSource()
        )
    }
}

fun Source.sourceToDbSourceEntity(): DbSource {
    return DbSource(
        id = id, name = name
    )
}

fun DbSource.dbSourceEntityToSource(): Source {
    return Source(
        id = id, name = name
    )
}

fun List<Article>.articleToDbArticle(): List<DbArticle> {
    return map {
        DbArticle(
            title = it.title,
            description = it.description,
            url = it.url,
            imageUrl = it.imageUrl,
            source = it.source.sourceToDbSourceEntity()
        )
    }
}
