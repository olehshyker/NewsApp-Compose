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
