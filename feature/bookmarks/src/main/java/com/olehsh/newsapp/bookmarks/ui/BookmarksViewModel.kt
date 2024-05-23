package com.olehsh.newsapp.bookmarks.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olehsh.newsapp.common.Result.Error
import com.olehsh.newsapp.common.Result.Loading
import com.olehsh.newsapp.common.Result.Success
import com.olehsh.newsapp.common.asResult
import com.olehsh.newsapp.domain.GetBookmarksListUseCase
import com.olehsh.newsapp.domain.UpdateBookmarkUseCase
import com.olehsh.newsapp.model.NewsArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    getBookmarksListUseCase: GetBookmarksListUseCase,
    private val updateBookmarkUseCase: UpdateBookmarkUseCase,
) : ViewModel() {

    val uiState: StateFlow<BookmarksUiState> = getBookmarksListUseCase()
        .asResult()
        .map { result ->
            when (result) {
                is Error -> BookmarksUiState.Error(result.exception?.message)
                Loading -> BookmarksUiState.Loading
                is Success -> BookmarksUiState.Success(result.data)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BookmarksUiState.Loading
        )

    fun updateBookmark(article: NewsArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            updateBookmarkUseCase(article)
        }
    }
}