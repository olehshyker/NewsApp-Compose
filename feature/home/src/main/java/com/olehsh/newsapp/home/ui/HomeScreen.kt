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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.olehsh.newsapp.designsystem.components.NewsAppBar
import com.olehsh.newsapp.home.R
import com.olehsh.newsapp.home.ui.components.HeadlineCard
import com.olehsh.newsapp.home.ui.components.NewsListItem
import com.olehsh.newsapp.home.ui.components.TitleText
import com.olehsh.newsapp.model.ArticleSource
import com.olehsh.newsapp.model.NewsArticle

@Composable
fun HomeScreen(
  modifier: Modifier = Modifier,
  homeViewModel: HomeViewModel = hiltViewModel(),
  onArticleClicked: (NewsArticle) -> Unit = {},
) {
  val homeUiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()
  val trendingNewsUiState by homeViewModel.trendingNewsUiState.collectAsStateWithLifecycle()
  val headlinesList by homeViewModel.headlinesList.collectAsStateWithLifecycle()
  val lazyPagingItems = homeViewModel.trendingNewsList.collectAsLazyPagingItems()

  HomeContent(
    homeUiState = homeUiState,
    newsPagingUiState = trendingNewsUiState,
    headlinesList = headlinesList,
    lazyPagingItems = lazyPagingItems,
    onArticleClicked = onArticleClicked,
  )
}

@Composable
internal fun HomeContent(
  homeUiState: HomeUiState,
  newsPagingUiState: NewsPagingUiState,
  headlinesList: List<NewsArticle>,
  lazyPagingItems: LazyPagingItems<NewsArticle>,
  onArticleClicked: (NewsArticle) -> Unit,
) {
  val pagerState = rememberPagerState(pageCount = { headlinesList.size })

  Column(modifier = Modifier.fillMaxSize()) {
    NewsAppBar(title = stringResource(id = com.olehsh.newsapp.designsystem.R.string.daily_news))

    HorizontalPager(
      state = pagerState,
      pageSpacing = 8.dp,
      contentPadding = PaddingValues(32.dp),
    ) { page ->
      HeadlineCard(
        article = headlinesList[page],
        onArticleClick = { onArticleClicked.invoke(it) },
      )
    }

    TitleText(title = stringResource(id = R.string.txt_trending))

    LazyColumn(
      modifier = Modifier.fillMaxWidth(),
      contentPadding = PaddingValues(8.dp),
    ) {
      if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
        item {
          CircularProgressIndicator(
            modifier = Modifier
              .fillMaxWidth()
              .wrapContentWidth(Alignment.CenterHorizontally),
          )
        }
      }

      items(
        count = lazyPagingItems.itemCount,
        key = lazyPagingItems.itemKey { it.url },
      ) { index ->
        lazyPagingItems[index]?.let { article ->
          NewsListItem(article, onArticleClicked = {
            onArticleClicked.invoke(it)
          })
        }
      }

      if (lazyPagingItems.loadState.append == LoadState.Loading) {
        item {
          CircularProgressIndicator(
            modifier = Modifier
              .fillMaxWidth()
              .wrapContentWidth(Alignment.CenterHorizontally),
          )
        }
      }
    }
  }
}

@Preview
@Composable
fun PreviewHeadLineCard() {
  val article = NewsArticle(
    author = "BBC News",
    content = "A statue of Queen Elizabeth II, thought to be the first permanent memorial to the late monarch, has been unveiled in Rutland. \\r\\nThe 7ft (2.1m) bronze monument was placed outside a library in Oakham, … [+1572 chars]",
    description = "The statue in Rutland was unveiled in a formal ceremony on the late monarch's birthday.",
    publishedAt = "2024-04-22T12:07:20.2316463Z",
    source = ArticleSource("bbc-news", "BBC News"),
    title = "Rutland: Hundreds attend Queen Elizabeth II statue unveiling",
    url = "https://www.bbc.co.uk/news/uk-england-leicestershire-68870129",
    imageUrl = "https://ichef.bbci.co.uk/news/1024/branded_news/14730/production/_133206738_image-1.jpg",

  )
  HeadlineCard(
    article = article,
  )
}
