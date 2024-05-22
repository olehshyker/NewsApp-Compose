package com.olehsh.newsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.olehsh.newsapp.database.dao.NewsArticlesDao
import com.olehsh.newsapp.database.dao.NewsArticlesRemoteKeysDao
import com.olehsh.newsapp.database.dao.NewsBookmarksDao
import com.olehsh.newsapp.database.dao.RecentNewsSearchDao
import com.olehsh.newsapp.database.entity.NewsArticleEntity
import com.olehsh.newsapp.database.entity.NewsBookmarkEntity
import com.olehsh.newsapp.database.entity.NewsRemoteKeysEntity
import com.olehsh.newsapp.database.entity.RecentNewsSearchEntity

@Database(
    entities = [NewsArticleEntity::class, NewsRemoteKeysEntity::class, RecentNewsSearchEntity::class, NewsBookmarkEntity::class],
    version = 3,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsArticlesDao
    abstract fun newsRemoteKeysDao(): NewsArticlesRemoteKeysDao
    abstract fun recentNewsSearchDao(): RecentNewsSearchDao
    abstract fun newsBookmarksDao(): NewsBookmarksDao
}