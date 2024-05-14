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
package com.olehsh.newsapp.model

data class NewsArticle(
  val author: String,
  val content: String,
  val description: String,
  val publishedAt: String,
  val source: ArticleSource,
  val title: String,
  val url: String,
  val imageUrl: String,
)

data class ArticleSource(
  val id: String,
  val name: String,
)
