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
package com.olehsh.newsapp.bottombar.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppNavigationBar(
  modifier: Modifier = Modifier,
  content: @Composable RowScope.() -> Unit,
) {
  NavigationBar(
    modifier = modifier.wrapContentWidth(),
    windowInsets = WindowInsets(0.dp),
    content = content,
  )
}

@Composable
fun RowScope.AppNavigationBarItem(
  label: String? = null,
  onSelected: () -> Boolean,
  onClick: () -> Unit,
  icon: @Composable () -> Unit,
) {
  NavigationBarItem(
    label = {
      label?.let {
        Text(
          text = it,
          style = MaterialTheme.typography.bodySmall,
        )
      }
    },
    selected = onSelected.invoke(),
    onClick = onClick,
    icon = icon,
    colors = NavigationBarItemDefaults.colors(),
    modifier = Modifier
      .fillMaxWidth(),
  )
}
