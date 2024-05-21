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
package com.olehsh.newsapp.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.olehsh.newsapp.model.NewsArticle

@Composable
fun HeadlineCard(
  article: NewsArticle,
  modifier: Modifier = Modifier,
  onArticleClick: (NewsArticle) -> Unit = {},
) {
  Card(
    onClick = {
      onArticleClick.invoke(article)
    },
    modifier = modifier
      .fillMaxWidth()
      .height(220.dp),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = MaterialTheme.colorScheme.onSurface,
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize(),
    ) {
      ArticlePoster(
        imageUrl = article.imageUrl,
      )

      TitleCardText(article.title, modifier = Modifier.align(Alignment.BottomStart))
    }
  }
}

@Composable
private fun TitleCardText(title: String, modifier: Modifier = Modifier) {
  Text(
    modifier = modifier
      .fillMaxWidth()
      .background(
        Brush.verticalGradient(
          0F to Color.Transparent,
          .5F to Color.Black.copy(alpha = 0.5F),
          1F to Color.Black.copy(alpha = 0.8F),
        ),
      )
      .padding(horizontal = 16.dp, vertical = 8.dp),
    text = title,
    color = Color.White,
    fontWeight = FontWeight.Bold,
    style = MaterialTheme.typography.titleMedium.copy(
      shadow = Shadow(
        color = Color.Black,
        blurRadius = 3f,
      ),
      textAlign = TextAlign.Start,
    ),
    maxLines = 2,
    overflow = TextOverflow.Ellipsis,
  )
}
