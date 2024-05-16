/*
 * Copyright 2024 olehshyker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.olehsh.newsapp.data.repository.home

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.olehsh.newsapp.database.AppDatabase
import com.olehsh.newsapp.database.entity.NewsArticleEntity
import com.olehsh.newsapp.database.entity.NewsRemoteKeysEntity
import com.olehsh.newsapp.database.entity.mappers.asEntity
import com.olehsh.newsapp.network.api.NewsApi
import com.olehsh.newsapp.network.toDomain
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewsArticlesRemoteMediator(
  private val newsApi: NewsApi,
  private val appDatabase: AppDatabase,
) : RemoteMediator<Int, NewsArticleEntity>() {
  override suspend fun load(
    loadType: LoadType,
    state: PagingState<Int, NewsArticleEntity>,
  ): MediatorResult {
    val page: Int = when (loadType) {
      LoadType.REFRESH -> 1
      LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
      LoadType.APPEND -> {
        val remoteKeys = getRemoteKeyForLastItem(state)
        val nextKey = remoteKeys?.nextKey
        nextKey
          ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
      }
    }

    try {
      val apiResponse = newsApi.getNewsList(page = page)
      val newsArticles = apiResponse.articles.map { it.toDomain() }
      val endOfPaginationReached = newsArticles.size < state.config.pageSize
      val prevKey = if (page > 1) page - 1 else null
      val nextKey = if (endOfPaginationReached) null else page + 1
      val remoteKeys = newsArticles.map {
        NewsRemoteKeysEntity(
          articleUrl = it.url,
          prevKey = prevKey,
          nextKey = nextKey,
        )
      }

      appDatabase.withTransaction {
        if (loadType == LoadType.REFRESH) {
          appDatabase.newsRemoteKeysDao().clearRemoteKeys()
          appDatabase.newsDao().clearArticles()
        }

        appDatabase.newsRemoteKeysDao().insertAll(remoteKeys)

        appDatabase.newsDao()
          .insertArticlesList(newsArticles.map { it.asEntity() })
      }

      return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
    } catch (error: IOException) {
      return MediatorResult.Error(error)
    }
  }

  private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, NewsArticleEntity>): NewsRemoteKeysEntity? {
    return state.lastItemOrNull()?.let { article ->
      appDatabase.newsRemoteKeysDao().remoteKeysForArticleUrl(article.url)
    }
  }
}
