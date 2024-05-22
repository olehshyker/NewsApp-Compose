package com.olehsh.newsapp.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_articles", primaryKeys = ["title", "publishedAt"])
data class NewsArticleEntity(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    @Embedded("source_")
    val source: ArticleSourceEntity,
    val title: String,
    val url: String,
    val imageUrl: String,
    val isHeadLine: Int = 0,
    val isBookmarked: Int = 0,
)

data class ArticleSourceEntity(
    val id: String?,
    val name: String,
)
