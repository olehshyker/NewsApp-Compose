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

    @Query("SELECT * FROM news_articles WHERE isHeadLine = 0")
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