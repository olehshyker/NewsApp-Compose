package com.olehsh.newsapp.database.entity

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "news_bookmarks", primaryKeys = ["title", "publishedAt"])
data class NewsBookmarkEntity(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    @Embedded("source_")
    val source: NewsBookmarkSourceEntity,
    val title: String,
    val url: String,
    val imageUrl: String,
    val isHeadLine: Int = 0,
)

data class NewsBookmarkSourceEntity(
    val id: String?,
    val name: String,
)
