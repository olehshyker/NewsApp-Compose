package com.olehsh.newsapp.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olehsh.newsapp.common.toTimeAgo
import com.olehsh.newsapp.designsystem.R
import com.olehsh.newsapp.designsystem.components.NewsAppBar
import com.olehsh.newsapp.designsystem.components.NewsListItem
import com.olehsh.newsapp.model.NewsArticle

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onArticleClicked: (String) -> Unit = {},
) {

    val searchUiState by searchViewModel.searchUiState.collectAsStateWithLifecycle()
    val searchQuery by searchViewModel.searchQuery.collectAsStateWithLifecycle()

    SearchContent(
        searchUiState = searchUiState,
        searchQuery = searchQuery,
        onArticleClicked = onArticleClicked,
        onSearchTriggered = searchViewModel::onSearchTriggered,
        onSearchQueryChanged = searchViewModel::onSearchQueryChange,
        onBookmarkClicked = searchViewModel::updateBookmark
    )
}

@Composable
internal fun SearchContent(
    searchUiState: SearchUiState,
    searchQuery: String = "",
    onArticleClicked: (String) -> Unit,
    onSearchQueryChanged: (String) -> Unit = {},
    onSearchTriggered: (String) -> Unit,
    onBookmarkClicked: (NewsArticle) -> Unit,
) {

    Column(modifier = Modifier.fillMaxSize()) {

        NewsAppBar(title = stringResource(id = R.string.search))

        SearchTextField(
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchTriggered = onSearchTriggered,
            searchQuery = searchQuery,
        )

        when (searchUiState) {
            SearchUiState.EmptyQuery,
            is SearchUiState.Error -> Unit

            SearchUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }

            is SearchUiState.Success -> {
                if (searchUiState.articles.isEmpty()) {
                    EmptySearchResultBody(searchQuery = searchQuery)
                } else {
                    SearchResultBody(
                        articles = searchUiState.articles,
                        onArticleClicked = onArticleClicked,
                        searchQuery = searchQuery,
                        onBookmarkClicked = onBookmarkClicked
                    )
                }
            }
        }
    }
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit,
    searchQuery: String,
    onSearchTriggered: (String) -> Unit,
) {
    val focusRequester = remember {
        FocusRequester()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchTriggered(searchQuery)
    }

    TextField(
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChanged("") }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        value = searchQuery,
        onValueChange = { value ->
            if (!value.contains("\n")) {
                onSearchQueryChanged(value)
            }
        },
        placeholder = {
                Text(text = stringResource(id = R.string.hint))
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    onSearchExplicitlyTriggered()
                    true
                } else {
                    false
                }
            },
        shape = RoundedCornerShape(32.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchExplicitlyTriggered()
            }
        ),
        maxLines = 1,
        singleLine = true
    )
}

@Composable
fun EmptySearchResultBody(
    searchQuery: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 48.dp),
    ) {
        val message = stringResource(id = R.string.txt_search_not_found, searchQuery)
        val start = message.indexOf(searchQuery)
        Text(
            text = AnnotatedString(
                text = message,
                spanStyles = listOf(
                    AnnotatedString.Range(
                        SpanStyle(fontWeight = FontWeight.Bold),
                        start = start,
                        end = start + searchQuery.length,
                    ),
                ),
            ),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 24.dp),
        )
    }
}

@Composable
private fun SearchResultBody(
    articles: List<NewsArticle>,
    onArticleClicked: (String) -> Unit,
    onBookmarkClicked: (NewsArticle) -> Unit,
    searchQuery: String = "",
) {

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
    ) {

        items(
            count = articles.size,
            key = { index ->
                articles[index].url + articles[index].publishedAt
            },
            itemContent = { index ->
                val article = articles[index]
                NewsListItem(
                    title = article.title,
                    description = article.description,
                    articleUrl = article.url,
                    imageUrl = article.imageUrl,
                    publishedTimeFormatted = context.toTimeAgo(article.publishedAt),
                    isBookmarked = article.isBookmarked,
                    onArticleClicked = {
                        onArticleClicked.invoke(it)
                    },
                    onBookmarkClicked = {
                        onBookmarkClicked.invoke(article)
                    })
            })
    }

}
