package com.olehsh.newsapp.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.olehsh.newsapp.database.entity.NewsArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticlesList(articlesList: List<NewsArticleEntity>)

    @Query("""
        SELECT 
    a.author,
    a.content,
    a.description,
    a.publishedAt,
    a.source_id,
    a.source_name,
    a.title,
    a.url,
    a.imageUrl,
    a.isHeadLine,
    CASE 
        WHEN b.url IS NOT NULL THEN 1
        ELSE 0
    END AS isBookmarked
FROM news_articles a
LEFT JOIN news_bookmarks b
ON a.url = b.url
    """)
    fun getPagingArticlesList(): PagingSource<Int, NewsArticleEntity>
    @Query("SELECT * FROM news_articles WHERE isHeadLine = 1")
    fun getHeadlinesArticlesList(): Flow<List<NewsArticleEntity>>

    @Query("SELECT * FROM news_articles")
    suspend fun getAllArticlesList(): List<NewsArticleEntity>
    @Query("SELECT * FROM news_articles WHERE url = :url")
    fun getArticleDetailsById(url: String): Flow<NewsArticleEntity>

    @Query("DELETE FROM news_articles WHERE isHeadLine = 0")
    suspend fun clearArticles()
    @Query("DELETE FROM news_articles WHERE isHeadLine = 1")
    suspend fun clearHeadlines()
}