package com.olehsh.newsapp.home

import com.olehsh.newsapp.model.NewsArticle


sealed interface ArticleDetailsUiState {

    data object Idle : ArticleDetailsUiState

    data object Loading : ArticleDetailsUiState

    data class Success(val newsArticle: NewsArticle) : ArticleDetailsUiState

    data class Error(val message: String?) : ArticleDetailsUiState
}