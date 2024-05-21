package com.olehsh.newsapp.search.ui

import com.olehsh.newsapp.model.NewsArticle

sealed interface SearchUiState {

    data object Loading : SearchUiState

    data object EmptyQuery : SearchUiState

    data class Success(
        val articles: List<NewsArticle>,
    ): SearchUiState

    data class Error(val message: String?) : SearchUiState
}