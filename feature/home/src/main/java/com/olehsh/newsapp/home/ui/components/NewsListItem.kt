package com.olehsh.newsapp.home.ui.components

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.olehsh.newsapp.common.toTimeAgo
import com.olehsh.newsapp.designsystem.components.CoilAsyncImage
import com.olehsh.newsapp.model.NewsArticle

@Composable
fun NewsListItem(
    article: NewsArticle, onArticleClicked: (NewsArticle) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = { onArticleClicked(article) }),
        shape = RoundedCornerShape(8.dp)
    ) {

        val context = LocalContext.current

        Row(verticalAlignment = Alignment.CenterVertically) {
            CoilAsyncImage(
                imageUrl = article.imageUrl,
                contentDescription = article.description,
                modifier = Modifier
                    .size(width = 140.dp, height = 120.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = article.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = article.description,
                    maxLines = 2,
                    modifier = Modifier.padding(top = 8.dp),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = context.toTimeAgo(article.publishedAt),
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}