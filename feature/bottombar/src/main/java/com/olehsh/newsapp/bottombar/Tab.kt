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
package com.olehsh.newsapp.bottombar

enum class Tab(
  val label: String,
  val icon: Int,
  val route: String,
) {
  HOME(label = "Home", icon = R.drawable.ic_home_24, "home_route"),
  SEARCH(label = "Search", icon = R.drawable.ic_search_24, "search_route"),
  BOOKMARKS(label = "Bookmarks", icon = R.drawable.ic_bookmarks_24, "bookmarks_route"),
}
