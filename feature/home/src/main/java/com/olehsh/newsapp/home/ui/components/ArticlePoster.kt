package com.olehsh.newsapp.home.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.olehsh.newsapp.designsystem.R
import com.olehsh.newsapp.designsystem.components.CoilAsyncImage


@Composable
fun ArticlePoster(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    CoilAsyncImage(
        placeholder = painterResource(R.drawable.ic_placeholder),
        imageUrl = imageUrl,
        contentDescription = null,
        modifier = modifier
            .fillMaxSize(),
    )
}