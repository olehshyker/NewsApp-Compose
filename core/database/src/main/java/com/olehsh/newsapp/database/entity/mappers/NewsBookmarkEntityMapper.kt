package com.olehsh.newsapp.database.entity.mappers

import com.olehsh.newsapp.database.entity.NewsBookmarkEntity
import com.olehsh.newsapp.database.entity.NewsBookmarkSourceEntity
import com.olehsh.newsapp.database.entity.RecentNewsSearchEntity
import com.olehsh.newsapp.database.entity.RecentSearchArticleSourceEntity
import com.olehsh.newsapp.model.ArticleSource
import com.olehsh.newsapp.model.NewsArticle

object NewsBookmarkEntityMapper : EntityMapper<NewsArticle, NewsBookmarkEntity> {

  override fun asEntity(domain: NewsArticle): NewsBookmarkEntity {
    return NewsBookmarkEntity(
      author = domain.author,
      content = domain.content,
      description = domain.description,
      publishedAt = domain.publishedAt,
      source = NewsBookmarkSourceEntity(domain.source.id, domain.source.name.orEmpty()),
      title = domain.title,
      url = domain.url,
      imageUrl = domain.imageUrl,
    )
  }

  override fun asDomain(entity: NewsBookmarkEntity): NewsArticle {
    return NewsArticle(
      author = entity.author,
      content = entity.content,
      description = entity.description,
      publishedAt = entity.publishedAt,
      source = ArticleSource(entity.source.id, entity.source.name),
      title = entity.title,
      url = entity.url,
      imageUrl = entity.imageUrl,
    )
  }
}

fun NewsArticle.asBookmarkEntity(): NewsBookmarkEntity {
  return NewsBookmarkEntityMapper.asEntity(this)
}

fun NewsBookmarkEntity.asDomain(): NewsArticle {
  return NewsBookmarkEntityMapper.asDomain(this)
}
