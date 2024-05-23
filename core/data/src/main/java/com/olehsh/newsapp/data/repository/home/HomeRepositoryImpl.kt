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
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.olehsh.newsapp.database.AppDatabase
import com.olehsh.newsapp.database.entity.mappers.asDomain
import com.olehsh.newsapp.database.entity.mappers.asEntity
import com.olehsh.newsapp.model.NewsArticle
import com.olehsh.newsapp.network.api.NewsApi
import com.olehsh.newsapp.network.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
  private val newsApi: NewsApi,
  private val appDatabase: AppDatabase,
) : HomeRepository {

  override fun fetchTopHeadlines(): Flow<List<NewsArticle>> {
    return appDatabase.newsDao().getHeadlinesArticlesList()
      .map { articles -> articles.map { it.asDomain() } }
  }

  override suspend fun syncHeadlines() {
    val headlinesList = newsApi.getTopHeadlines().articles.map { it.toDomain() }

    val entities = headlinesList.map { it.asEntity().copy(isHeadLine = 1) }

    appDatabase.withTransaction {
      appDatabase.newsDao().clearHeadlines()
      appDatabase.newsDao()
        .insertArticlesList(entities)
    }
  }

  @OptIn(ExperimentalPagingApi::class)
  override fun fetchNewsList(): Flow<PagingData<NewsArticle>> = Pager(
    config = PagingConfig(
      pageSize = 20,
    ),
    pagingSourceFactory = {
      appDatabase.newsDao().getPagingArticlesList()
    },
    remoteMediator = NewsArticlesRemoteMediator(newsApi, appDatabase),
  ).flow.map { it.map { article -> article.asDomain() } }

  override fun searchArticles(query: String): Flow<PagingData<NewsArticle>> {
    TODO("Not yet implemented")
  }
}
