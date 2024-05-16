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
package com.olehsh.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.olehsh.newsapp.home.navigation.HOME_ROUTE
import com.olehsh.newsapp.home.navigation.articleDetailsScreen
import com.olehsh.newsapp.home.navigation.homeScreen
import com.olehsh.newsapp.home.navigation.navigateToDetails

@Composable
fun AppNavHost(
  navController: NavHostController,
  modifier: Modifier = Modifier,
  startDestination: String = HOME_ROUTE,
) {
  NavHost(
    navController = navController,
    startDestination = startDestination,
    modifier = modifier,
  ) {
    homeScreen(
      onArticleClicked = {
        navController.navigateToDetails(it)
      },
    )
    articleDetailsScreen(onBackClicked = { navController.navigateUp() })
  }
}
