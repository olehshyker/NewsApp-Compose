package com.olehsh.newsapp.bookmarks.ui

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
fun BookmarksScreen(
    bookmarksViewModel: BookmarksViewModel = hiltViewModel(),
    onArticleClicked: (String) -> Unit = {},
) {

    val uiState by bookmarksViewModel.uiState.collectAsStateWithLifecycle()

    BookmarksContent(
        uiState = uiState,
        onArticleClicked = onArticleClicked,
        onBookmarkClicked = bookmarksViewModel::updateBookmark
    )
}

@Composable
internal fun BookmarksContent(
    uiState: BookmarksUiState,
    onArticleClicked: (String) -> Unit,
    onBookmarkClicked: (NewsArticle) -> Unit,
) {

    Column(modifier = Modifier.fillMaxSize()) {

        NewsAppBar(title = stringResource(id = R.string.bookmarks))

        when (uiState) {
            is BookmarksUiState.Error -> Unit

            BookmarksUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }

            is BookmarksUiState.Success -> {
                if (uiState.articles.isEmpty()) {
                    EmptyResultBody()
                } else {
                    BookmarksResultBody(
                        articles = uiState.articles,
                        onArticleClicked = onArticleClicked,
                        onBookmarkClicked = onBookmarkClicked
                    )
                }
            }
        }
    }
}
@Composable
fun EmptyResultBody(
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 48.dp),
    ) {
        val message = stringResource(id = R.string.txt_bookmarks_not_found)
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 24.dp),
        )
    }
}

@Composable
private fun BookmarksResultBody(
    articles: List<NewsArticle>,
    onArticleClicked: (String) -> Unit,
    onBookmarkClicked: (NewsArticle) -> Unit,
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
                    modifier = Modifier.animateItem(),
                    title = article.title,
                    description = article.description,
                    articleUrl = article.url,
                    imageUrl = article.imageUrl,
                    publishedTimeFormatted = context.toTimeAgo(article.publishedAt),
                    onArticleClicked = {
                        onArticleClicked.invoke(it)
                    },
                    isBookmarked = article.isBookmarked,
                    onBookmarkClicked = {
                        onBookmarkClicked.invoke(article)
                    })
            })
    }

}
