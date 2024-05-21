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
package com.olehsh.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.olehsh.newsapp.bottombar.Tab
import com.olehsh.newsapp.bottombar.components.AppNavigationBar
import com.olehsh.newsapp.bottombar.components.AppNavigationBarItem
import com.olehsh.newsapp.designsystem.theme.NewsAppTheme
import com.olehsh.newsapp.home.navigation.navigateToHome
import com.olehsh.newsapp.navigation.AppNavHost
import com.olehsh.newsapp.search.navigation.navigateToSearch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    setContent {
      NewsAppTheme {
        val navHostController = rememberNavController()

        Scaffold(
          contentWindowInsets = WindowInsets(0.dp),
          bottomBar = {
            val currentRoute =
              navHostController.currentBackStackEntryAsState().value?.destination?.route
            AppNavigationBar(
              modifier = Modifier
                .navigationBarsPadding(),
            ) {
              for (tab in remember { Tab.entries }) {
                AppNavigationBarItem(
                  onSelected = { currentRoute == tab.route },
                  onClick = {
                    if (currentRoute == tab.route) return@AppNavigationBarItem
                    when (tab) {
                      Tab.HOME -> navHostController.navigateToHome()
                      Tab.SEARCH -> {
                        navHostController.navigateToSearch()
                      }

                      Tab.BOOKMARKS -> {
                        /* TODO */
                      }
                    }
                  },
                  icon = {
                    Icon(
                      painterResource(id = tab.icon),
                      contentDescription = tab.label,
                    )
                  },
                )
              }
            }
          },
          modifier = Modifier.fillMaxSize(),
        ) { paddingValues ->
          AppNavHost(
            navController = navHostController,
            modifier = Modifier
              .padding(paddingValues)
              .fillMaxSize(),
          )
        }
      }
    }
  }
}
