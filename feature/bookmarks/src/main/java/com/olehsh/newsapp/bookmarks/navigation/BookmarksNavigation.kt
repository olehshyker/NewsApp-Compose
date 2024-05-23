package com.olehsh.newsapp.bookmarks.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.olehsh.newsapp.bookmarks.ui.BookmarksScreen

const val BOOKMARKS_ROUTE = "bookmarks_route"

fun NavController.navigateToBookmarks(navOptions: NavOptions = NavOptions.Builder().build()) =
    navigate(BOOKMARKS_ROUTE, navOptions)

fun NavGraphBuilder.bookmarksScreen(
    onArticleClicked: (String) -> Unit,
) {
    composable(route = BOOKMARKS_ROUTE) {
        BookmarksScreen(onArticleClicked = onArticleClicked)
    }
}
