package com.olehsh.newsapp.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olehsh.newsapp.designsystem.components.CoilAsyncImage
import com.olehsh.newsapp.designsystem.components.NewsAppBar


@Composable
fun ArticleDetailsScreen(
    viewModel: ArticleDetailsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ArticleDetailsContent(uiState, onBackClicked)
}

@Composable
internal fun ArticleDetailsContent(
    uiState: ArticleDetailsUiState,
    onBackClicked: () -> Unit = {}
) {

    Column(modifier = Modifier.fillMaxSize()) {

        NewsAppBar(
            navigationIcon = {
                IconButton(onClick = { onBackClicked.invoke() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "back")
                }
            }
        )

        when (uiState) {
            is ArticleDetailsUiState.Success -> {

                CoilAsyncImage(
                    imageUrl = uiState.newsArticle.imageUrl,
                    contentDescription = uiState.newsArticle.imageUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                TitleText(uiState.newsArticle.title)

                DescriptionText(title = uiState.newsArticle.content)
            }

            is ArticleDetailsUiState.Error -> {
            }

            ArticleDetailsUiState.Idle -> {
            }

            ArticleDetailsUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }
    }
}

@Composable
fun TitleText(title: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        text = title,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
fun DescriptionText(title: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        text = title,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyMedium,
    )
}