package com.olehsh.newsapp.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olehsh.newsapp.common.Result
import com.olehsh.newsapp.common.asResult
import com.olehsh.newsapp.domain.GetArticleDetailsUseCase
import com.olehsh.newsapp.home.navigation.ArticleDetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getArticleDetailsUseCase: GetArticleDetailsUseCase,
) : ViewModel() {

    private val args: ArticleDetailsArgs by lazy { ArticleDetailsArgs(savedStateHandle = savedStateHandle) }

    val uiState: StateFlow<ArticleDetailsUiState> =
        getArticleDetailsUseCase(args.articleId)
            .asResult()
            .map {
                when (it) {
                    is Result.Error -> ArticleDetailsUiState.Error(it.exception?.message.orEmpty())
                    Result.Loading -> ArticleDetailsUiState.Loading
                    is Result.Success -> ArticleDetailsUiState.Success(it.data)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ArticleDetailsUiState.Idle,
            )
}