package com.olehsh.newsapp.search.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olehsh.newsapp.common.Result.Error
import com.olehsh.newsapp.common.Result.Loading
import com.olehsh.newsapp.common.Result.Success
import com.olehsh.newsapp.common.asResult
import com.olehsh.newsapp.domain.GetBookmarksListUseCase
import com.olehsh.newsapp.domain.SearchArticlesUseCase
import com.olehsh.newsapp.domain.UpdateBookmarkUseCase
import com.olehsh.newsapp.model.NewsArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArticlesUseCase: SearchArticlesUseCase,
    private val getBookmarksListUseCase: GetBookmarksListUseCase,
    private val updateBookmarkUseCase: UpdateBookmarkUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    val searchUiState: StateFlow<SearchUiState> = searchQuery.flatMapLatest { query ->
        if (query.length < SEARCH_QUERY_MIN_LENGTH) {
            flowOf(SearchUiState.EmptyQuery)
        } else {
            combine(getBookmarksListUseCase(), searchArticlesUseCase(query)) { bookmarks, searchList ->
                searchList.map { it.copy(isBookmarked = bookmarks.any { bookmark -> it.url == bookmark.url }) }
            }.asResult()
                .map { result ->
                    when (result) {
                        is Error -> SearchUiState.Error(result.exception?.message)
                        Loading -> SearchUiState.Loading
                        is Success -> SearchUiState.Success(result.data)
                    }
                }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchUiState.Loading
    )

    fun onSearchQueryChange(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }

    fun onSearchTriggered(query: String) {
    }

    fun updateBookmark(article: NewsArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            updateBookmarkUseCase(article)
        }
    }
}

private const val SEARCH_QUERY_MIN_LENGTH = 2
private const val SEARCH_QUERY = "searchQuery"