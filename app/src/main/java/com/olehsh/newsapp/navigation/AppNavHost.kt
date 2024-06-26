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
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.olehsh.newsapp.bookmarks.navigation.bookmarksScreen
import com.olehsh.newsapp.bookmarks.navigation.navigateToBookmarks
import com.olehsh.newsapp.bottombar.Tab
import com.olehsh.newsapp.home.navigation.HOME_ROUTE
import com.olehsh.newsapp.home.navigation.articleDetailsScreen
import com.olehsh.newsapp.home.navigation.homeScreen
import com.olehsh.newsapp.home.navigation.navigateToDetails
import com.olehsh.newsapp.home.navigation.navigateToHome
import com.olehsh.newsapp.model.SourceType
import com.olehsh.newsapp.search.navigation.navigateToSearch
import com.olehsh.newsapp.search.navigation.searchScreen

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
        navController.navigateToDetails(it, sourceType = SourceType.HOME)
      },
    )
    searchScreen(
      onArticleClicked = {
        navController.navigateToDetails(it, sourceType = SourceType.SEARCH)
      },
    )
    articleDetailsScreen(onBackClicked = { navController.navigateUp() })

    bookmarksScreen(
      onArticleClicked = {
        navController.navigateToDetails(it, sourceType = SourceType.BOOKMARKS)
      },
    )
  }
}

fun NavController.navigateToTopLevelDestination(tab: Tab) {
  val navOptions = navOptions {
    popUpTo(graph.findStartDestination().id) {
      inclusive = true
    }
    launchSingleTop = true
    restoreState = true
  }

  when (tab) {
    Tab.HOME -> navigateToHome(navOptions)
    Tab.SEARCH -> navigateToSearch(navOptions)
    Tab.BOOKMARKS -> navigateToBookmarks(navOptions)
  }
}
