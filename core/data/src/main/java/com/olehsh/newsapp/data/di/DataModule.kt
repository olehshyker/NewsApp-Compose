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
package com.olehsh.newsapp.data.di

import com.olehsh.newsapp.data.repository.bookmarks.BookmarksRepository
import com.olehsh.newsapp.data.repository.bookmarks.BookmarksRepositoryImpl
import com.olehsh.newsapp.data.repository.details.ArticleDetailsRepository
import com.olehsh.newsapp.data.repository.details.ArticleDetailsRepositoryImpl
import com.olehsh.newsapp.data.repository.home.HomeRepository
import com.olehsh.newsapp.data.repository.home.HomeRepositoryImpl
import com.olehsh.newsapp.data.repository.search.SearchRepository
import com.olehsh.newsapp.data.repository.search.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

  @Binds
  internal abstract fun bindsNewsRepository(newsRepositoryImpl: HomeRepositoryImpl): HomeRepository

  @Binds
  internal abstract fun bindsArticleDetailsRepository(articleDetailsRepository: ArticleDetailsRepositoryImpl): ArticleDetailsRepository

  @Binds
  internal abstract fun bindsSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository

  @Binds
  internal abstract fun bindsBookmarksRepository(bookmarksRepositoryImpl: BookmarksRepositoryImpl): BookmarksRepository
}
