package com.olehsh.newsapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.olehsh.newsapp.database.entity.NewsBookmarkEntity
import com.olehsh.newsapp.database.entity.RecentNewsSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsBookmarksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmarkEntity: NewsBookmarkEntity)

    @Query("SELECT * FROM news_bookmarks")
    suspend fun getAllBookmarksList(): Flow<List<NewsBookmarkEntity>>

    @Query("SELECT * FROM news_bookmarks WHERE url = :url")
    fun getArticleDetailsById(url: String): Flow<NewsBookmarkEntity>

    @Query("DELETE FROM news_bookmarks")
    suspend fun clearBookmarks()
}