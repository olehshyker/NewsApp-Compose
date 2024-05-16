package com.olehsh.newsapp.home.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.olehsh.newsapp.home.ArticleDetailsScreen
import com.olehsh.newsapp.model.NewsArticle
import java.net.URLDecoder
import java.net.URLEncoder

const val ARTICLE_DETAILS_ROUTE = "news_details_route"

internal const val articleIdArg = "articleId"

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

internal class ArticleDetailsArgs(val articleId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                URLDecoder.decode(
                    checkNotNull(savedStateHandle[articleIdArg]),
                    URL_CHARACTER_ENCODING
                )
            )
}

fun NavController.navigateToDetails(newsArticle: NewsArticle) {
    val encodedId = URLEncoder.encode(newsArticle.url, URL_CHARACTER_ENCODING)
    this.navigate("$ARTICLE_DETAILS_ROUTE/$encodedId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.articleDetailsScreen(onBackClicked: () -> Unit) {
    composable(
        route = "$ARTICLE_DETAILS_ROUTE/{$articleIdArg}",
        arguments = listOf(
            navArgument(articleIdArg) { type = NavType.StringType },
        ),
    ) {
        ArticleDetailsScreen(onBackClicked = onBackClicked)
    }
}
