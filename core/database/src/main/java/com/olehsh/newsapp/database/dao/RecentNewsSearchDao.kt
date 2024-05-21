package com.olehsh.newsapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.olehsh.newsapp.database.entity.RecentNewsSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentNewsSearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentNewsSearchList(articlesList: List<RecentNewsSearchEntity>)

    @Query("SELECT * FROM recent_news_search_articles")
    suspend fun getAllArticlesList(): List<RecentNewsSearchEntity>

    @Query("SELECT * FROM recent_news_search_articles WHERE url = :url")
    fun getArticleDetailsById(url: String): Flow<RecentNewsSearchEntity>

    @Query("DELETE FROM recent_news_search_articles")
    suspend fun clearRecentSearch()
}