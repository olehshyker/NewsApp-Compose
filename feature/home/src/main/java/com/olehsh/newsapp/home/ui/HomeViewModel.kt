package com.olehsh.newsapp.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.olehsh.newsapp.domain.GetNewsListUseCase
import com.olehsh.newsapp.domain.GetTopHeadlinesUseCase
import com.olehsh.newsapp.domain.SyncTopHeadlinesUseCase
import com.olehsh.newsapp.model.NewsArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val syncTopHeadlinesUseCase: SyncTopHeadlinesUseCase,
    getNewsListUseCase: GetNewsListUseCase,
) : ViewModel() {
    init {
        syncHeadLines()
    }

    val homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Idle)

    val headlinesList: StateFlow<List<NewsArticle>> =
        getTopHeadlinesUseCase()
            .onStart { homeUiState.tryEmit(HomeUiState.Loading) }
            .onCompletion { homeUiState.tryEmit(HomeUiState.Idle) }
            .catch { homeUiState.tryEmit(HomeUiState.Error(it.message)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList(),
            )

    val trendingNewsUiState: MutableStateFlow<NewsPagingUiState> =
        MutableStateFlow(NewsPagingUiState.Idle)

    val trendingNewsList: Flow<PagingData<NewsArticle>> = getNewsListUseCase()
        .onStart { trendingNewsUiState.tryEmit(NewsPagingUiState.Loading) }
        .onCompletion { throwable ->
            if (throwable == null) {
                trendingNewsUiState.tryEmit(NewsPagingUiState.Idle)
            } else {
                trendingNewsUiState.tryEmit(NewsPagingUiState.Error(throwable.message))
            }
        }.cachedIn(viewModelScope)

    private fun syncHeadLines() {
        viewModelScope.launch {
            syncTopHeadlinesUseCase()
        }
    }
}