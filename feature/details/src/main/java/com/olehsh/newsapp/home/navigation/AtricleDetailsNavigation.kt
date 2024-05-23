package com.olehsh.newsapp.home.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.olehsh.newsapp.home.ArticleDetailsScreen
import com.olehsh.newsapp.model.NewsArticle
import com.olehsh.newsapp.model.SourceType
import java.net.URLDecoder
import java.net.URLEncoder

const val ARTICLE_DETAILS_ROUTE = "news_details_route"

internal const val articleIdArg = "articleId"
internal const val sourceTypeArg = "sourceType"

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

internal class ArticleDetailsArgs(val articleId: String, val sourceType: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                URLDecoder.decode(
                    checkNotNull(savedStateHandle[articleIdArg]),
                    URL_CHARACTER_ENCODING
                ),
                URLDecoder.decode(
                    checkNotNull(savedStateHandle[sourceTypeArg]),
                    URL_CHARACTER_ENCODING
                )
            )
}

fun NavController.navigateToDetails(articleUrl: String, sourceType: SourceType) {
    val encodedId = URLEncoder.encode(articleUrl, URL_CHARACTER_ENCODING)
    this.navigate("$ARTICLE_DETAILS_ROUTE/$encodedId/${sourceType.name}") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.articleDetailsScreen(onBackClicked: () -> Unit) {
    composable(
        route = "$ARTICLE_DETAILS_ROUTE/{$articleIdArg}/{$sourceTypeArg}",
        arguments = listOf(
            navArgument(articleIdArg) { type = NavType.StringType },
            navArgument(sourceTypeArg) { type = NavType.StringType },
        ),
    ) {
        ArticleDetailsScreen(onBackClicked = onBackClicked)
    }
}
