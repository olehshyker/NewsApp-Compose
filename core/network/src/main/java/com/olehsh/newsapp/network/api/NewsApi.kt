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
package com.olehsh.newsapp.network.api

import com.olehsh.newsapp.network.ApiConstants
import com.olehsh.newsapp.network.model.HeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
  @GET("v2/top-headlines?sources=bbc-news")
  suspend fun getTopHeadlines(): HeadlinesResponse

  @GET("v2/everything")
  suspend fun getNewsList(
    @Query("q") query: String = "trending",
    @Query("page") page: Int = 1,
    @Query("pageSize") pageSize: Int = ApiConstants.DEFAULT_PAGE_SIZE,
  ): HeadlinesResponse
}
