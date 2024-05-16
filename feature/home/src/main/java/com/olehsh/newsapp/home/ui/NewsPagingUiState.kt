package com.olehsh.newsapp.home.ui

sealed interface NewsPagingUiState {

    data object Idle : NewsPagingUiState

    data object Loading : NewsPagingUiState

    data object Paginating : NewsPagingUiState

    data object EndOfPagination : NewsPagingUiState

    data class Error(val message: String?) : NewsPagingUiState
}
