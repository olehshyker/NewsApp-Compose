package com.olehsh.newsapp.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.olehsh.newsapp.search.ui.SearchScreen

const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions = NavOptions.Builder().build()) =
    navigate(SEARCH_ROUTE, navOptions)

fun NavGraphBuilder.searchScreen(
    onArticleClicked: (String) -> Unit,
) {
    composable(route = SEARCH_ROUTE) {
        SearchScreen(onArticleClicked = onArticleClicked)
    }
}
