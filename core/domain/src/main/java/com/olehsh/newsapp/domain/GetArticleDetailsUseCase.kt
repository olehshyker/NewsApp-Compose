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
package com.olehsh.newsapp.domain

import com.olehsh.newsapp.data.repository.details.ArticleDetailsRepository
import com.olehsh.newsapp.data.repository.search.SearchRepository
import com.olehsh.newsapp.model.NewsArticle
import com.olehsh.newsapp.model.SourceType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticleDetailsUseCase @Inject constructor(
  private val articleDetailsRepository: ArticleDetailsRepository,
  private val searchRepository: SearchRepository,
) {
  operator fun invoke(articleId: String, sourceType: SourceType): Flow<NewsArticle> {
    return when (sourceType) {
      SourceType.SEARCH -> searchRepository.getSearchArticleDetailsById(articleId)
      else -> articleDetailsRepository.getArticleDetailsById(articleId)
    }
  }
}
