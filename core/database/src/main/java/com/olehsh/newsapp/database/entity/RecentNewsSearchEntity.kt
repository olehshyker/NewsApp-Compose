package com.olehsh.newsapp.database.entity

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "recent_news_search_articles", primaryKeys = ["title", "publishedAt"])
data class RecentNewsSearchEntity(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    @Embedded("source_")
    val source: RecentSearchArticleSourceEntity,
    val title: String,
    val url: String,
    val imageUrl: String,
    val isHeadLine: Int = 0,
)

data class RecentSearchArticleSourceEntity(
    val id: String?,
    val name: String,
)
