package com.olehsh.newsapp.bookmarks.ui

import com.olehsh.newsapp.model.NewsArticle

sealed interface BookmarksUiState {

    data object Loading : BookmarksUiState

    data class Success(
        val articles: List<NewsArticle>,
    ): BookmarksUiState

    data class Error(val message: String?) : BookmarksUiState
}