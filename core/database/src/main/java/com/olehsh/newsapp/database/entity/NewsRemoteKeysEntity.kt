package com.olehsh.newsapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_remote_keys")
data class NewsRemoteKeysEntity(
    @PrimaryKey
    val articleUrl: String,
    val prevKey: Int?,
    val nextKey: Int?,
)