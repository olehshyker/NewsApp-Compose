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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Error
import coil.compose.AsyncImagePainter.State.Loading
import coil.compose.rememberAsyncImagePainter
import com.olehsh.newsapp.designsystem.R

@Composable
fun CoilAsyncImage(
  imageUrl: String,
  contentDescription: String?,
  modifier: Modifier = Modifier,
  placeholder: Painter = painterResource(R.drawable.ic_placeholder),
) {
  var isLoading by remember { mutableStateOf(true) }
  var isError by remember { mutableStateOf(false) }
  val imageLoader = rememberAsyncImagePainter(
    model = imageUrl,
    onState = { state ->
      isLoading = state is Loading
      isError = state is Error
    },
  )
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center,
  ) {
    if (isLoading) {
      // Display a progress bar while loading
      CircularProgressIndicator(
        modifier = Modifier
          .align(Alignment.Center)
          .size(40.dp),
        color = MaterialTheme.colorScheme.onBackground,
      )
    }
    Image(
      contentScale = ContentScale.Crop,
      painter = if (!isError) imageLoader else placeholder,
      contentDescription = contentDescription,
      modifier = Modifier.fillMaxSize(),
    )
  }
}
