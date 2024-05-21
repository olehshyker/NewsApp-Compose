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
package com.olehsh.newsapp.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.olehsh.newsapp.home.ui.HomeScreen
import com.olehsh.newsapp.model.NewsArticle

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions = NavOptions.Builder().build()) =
  navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen(onArticleClicked: (NewsArticle) -> Unit) {
  composable(route = HOME_ROUTE) {
    HomeScreen(onArticleClicked = onArticleClicked)
  }
}
