package com.olehsh.newsapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.olehsh.newsapp.database.entity.NewsRemoteKeysEntity

@Dao
interface NewsArticlesRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<NewsRemoteKeysEntity>)

    @Query("SELECT * FROM news_remote_keys WHERE articleUrl = :url")
    suspend fun remoteKeysForArticleUrl(url: String): NewsRemoteKeysEntity?

    @Query("DELETE FROM news_remote_keys")
    suspend fun clearRemoteKeys()
}