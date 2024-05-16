package com.olehsh.newsapp.database.di

import android.app.Application
import androidx.room.Room
import com.olehsh.newsapp.database.AppDatabase
import com.olehsh.newsapp.database.dao.NewsArticlesDao
import com.olehsh.newsapp.database.dao.NewsArticlesRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
    ): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "news.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsArticlesDao(appDatabase: AppDatabase): NewsArticlesDao {
        return appDatabase.newsDao()
    }

    @Provides
    @Singleton
    fun provideRemoteKeysDao(appDatabase: AppDatabase): NewsArticlesRemoteKeysDao {
        return appDatabase.newsRemoteKeysDao()
    }
}
