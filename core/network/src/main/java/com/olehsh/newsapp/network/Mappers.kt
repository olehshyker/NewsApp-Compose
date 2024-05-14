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
package com.olehsh.newsapp.network

import com.olehsh.newsapp.model.ArticleSource
import com.olehsh.newsapp.model.NewsArticle
import com.olehsh.newsapp.network.model.HeadlinesResponse

fun HeadlinesResponse.Article.toDomain() = NewsArticle(
  author = this.author.orEmpty(),
  content = this.content.orEmpty(),
  description = this.description.orEmpty(),
  publishedAt = this.publishedAt.orEmpty(),
  title = this.title.orEmpty(),
  url = this.url.orEmpty(),
  imageUrl = this.urlToImage.orEmpty(),
  source = this.source?.toDomain() ?: ArticleSource(),
)

fun HeadlinesResponse.Article.Source.toDomain() = ArticleSource(
  id = this.id,
  name = this.name,
)
