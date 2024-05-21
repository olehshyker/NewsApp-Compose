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
package com.olehsh.newsapp.data.repository.search

import com.olehsh.newsapp.database.dao.RecentNewsSearchDao
import com.olehsh.newsapp.database.entity.mappers.asDomain
import com.olehsh.newsapp.database.entity.mappers.asSearchEntity
import com.olehsh.newsapp.model.NewsArticle
import com.olehsh.newsapp.network.api.NewsApi
import com.olehsh.newsapp.network.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
  private val newsApi: NewsApi,
  private val recentNewsSearchDao: RecentNewsSearchDao,
) : SearchRepository {

  override fun searchArticles(query: String): Flow<List<NewsArticle>> = flow {
    val response = newsApi.getNewsList(query).articles.map { it.toDomain() }

    recentNewsSearchDao.clearRecentSearch()
    recentNewsSearchDao.insertRecentNewsSearchList(response.map { it.asSearchEntity() })

    emit(recentNewsSearchDao.getAllArticlesList().map { it.asDomain() })
  }

  override fun getSearchArticleDetailsById(articleId: String): Flow<NewsArticle> {
    return recentNewsSearchDao.getArticleDetailsById(articleId).map { it.asDomain() }
  }
}
