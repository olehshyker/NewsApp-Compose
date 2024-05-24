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
package com.olehsh.newsapp.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.olehsh.newsapp.domain.GetNewsListUseCase
import com.olehsh.newsapp.domain.GetTopHeadlinesUseCase
import com.olehsh.newsapp.domain.SyncTopHeadlinesUseCase
import com.olehsh.newsapp.domain.UpdateBookmarkUseCase
import com.olehsh.newsapp.model.NewsArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
  private val updateBookmarkUseCase: UpdateBookmarkUseCase,
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
      syncTopHeadlinesUseCase.invoke()
    }
  }
  fun updateBookmark(article: NewsArticle) {
    viewModelScope.launch(Dispatchers.IO) {
      updateBookmarkUseCase(article)
    }
  }
}
