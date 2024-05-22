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
package com.olehsh.newsapp.data.repository.bookmarks

import com.olehsh.newsapp.database.dao.NewsBookmarksDao
import com.olehsh.newsapp.database.entity.mappers.asBookmarkEntity
import com.olehsh.newsapp.database.entity.mappers.asDomain
import com.olehsh.newsapp.model.NewsArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class BookmarksRepositoryImpl @Inject constructor(private val bookmarksDao: NewsBookmarksDao) :
    BookmarksRepository {
    override fun addToBookmarks(article: NewsArticle) {
        bookmarksDao.insertBookmark(article.asBookmarkEntity())
    }

    override fun removeFromBookmarks(articleId: String) {
        bookmarksDao.removeFromBookmarksById(articleId)
    }

    override fun getBookmarkedArticles(): Flow<List<NewsArticle>> {
        return bookmarksDao.getAllBookmarksList()
            .map { list -> list.map { it.asDomain() }.map { it.copy(isBookmarked = true) } }
    }

    override fun getBookmarkedArticleDetailsById(articleId: String): Flow<NewsArticle> {
        return bookmarksDao.getArticleDetailsById(articleId).map { it.asDomain() }
    }
}
