package com.olehsh.newsapp.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.olehsh.newsapp.home.ui.HomeScreen
import com.olehsh.newsapp.model.NewsArticle

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions) =
    navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen(onArticleClicked: (NewsArticle) -> Unit) {
    composable(route = HOME_ROUTE) {
        HomeScreen(onArticleClicked = onArticleClicked)
    }
}
