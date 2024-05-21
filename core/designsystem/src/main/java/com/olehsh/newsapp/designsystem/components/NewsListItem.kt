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
package com.olehsh.newsapp.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun NewsListItem(
  title: String,
  description: String,
  articleUrl: String,
  imageUrl: String,
  publishedTimeFormatted: String,
  onArticleClicked: (String) -> Unit,
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
      .clickable(onClick = { onArticleClicked(articleUrl) }),
    shape = RoundedCornerShape(8.dp),
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      CoilAsyncImage(
        imageUrl = imageUrl,
        contentDescription = description,
        modifier = Modifier
          .size(width = 140.dp, height = 120.dp)
          .padding(8.dp)
          .clip(RoundedCornerShape(8.dp)),
      )
      Column(modifier = Modifier.padding(8.dp)) {
        Text(
          text = title,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
          style = MaterialTheme.typography.titleSmall,
        )

        Text(
          text = description,
          maxLines = 2,
          modifier = Modifier.padding(top = 8.dp),
          overflow = TextOverflow.Ellipsis,
          style = MaterialTheme.typography.bodySmall,
        )

        Text(
          text = publishedTimeFormatted,
          modifier = Modifier.padding(top = 8.dp),
          style = MaterialTheme.typography.labelSmall,
        )
      }
    }
  }
}
