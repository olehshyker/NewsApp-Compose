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
package com.olehsh.newsapp.network.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.olehsh.newsapp.network.BuildConfig
import com.olehsh.newsapp.network.api.NewsApi
import com.olehsh.newsapp.network.interceptor.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

  @Provides
  @Singleton
  fun providesJson(): Json = Json {
    ignoreUnknownKeys = true
  }

  @Provides
  @Singleton
  fun okHttpCallFactory(): OkHttpClient =
    OkHttpClient.Builder()
      .addInterceptor(TokenInterceptor())
      .addInterceptor(
        HttpLoggingInterceptor()
          .apply {
            if (BuildConfig.DEBUG) {
              setLevel(HttpLoggingInterceptor.Level.BODY)
            }
          },
      )
      .build()

  @Provides
  @Singleton
  fun provideNewsApi(json: Json, client: OkHttpClient): NewsApi = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .client(client)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()
    .create(NewsApi::class.java)

  @Provides
  @Singleton
  fun imageLoader(
    okHttpClient: OkHttpClient,
    @ApplicationContext application: Context,
  ): ImageLoader =
    ImageLoader.Builder(application)
      .okHttpClient(okHttpClient)
      .memoryCache {
        MemoryCache.Builder(application)
          .maxSizePercent(0.20)
          .build()
      }
      .diskCache {
        DiskCache.Builder()
          .directory(application.cacheDir.resolve("image_cache"))
          .maxSizeBytes(5 * 1024 * 1024)
          .build()
      }
      .respectCacheHeaders(false)
      .apply {
        if (BuildConfig.DEBUG) {
          logger(DebugLogger())
        }
      }
      .build()
}
