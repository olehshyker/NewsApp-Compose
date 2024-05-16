package com.olehsh.newsapp.database.entity.mappers

import com.olehsh.newsapp.database.entity.ArticleSourceEntity
import com.olehsh.newsapp.database.entity.NewsArticleEntity
import com.olehsh.newsapp.model.ArticleSource
import com.olehsh.newsapp.model.NewsArticle

object NewsArticleEntityMapper : EntityMapper<NewsArticle, NewsArticleEntity> {

  override fun asEntity(domain: NewsArticle): NewsArticleEntity {
    return NewsArticleEntity(
      author = domain.author,
      content = domain.content,
      description = domain.description,
      publishedAt = domain.publishedAt,
      source = ArticleSourceEntity(domain.source.id, domain.source.name.orEmpty()),
      title = domain.title,
      url = domain.url,
      imageUrl = domain.imageUrl,
    )
  }

  override fun asDomain(entity: NewsArticleEntity): NewsArticle {
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

fun NewsArticle.asEntity(): NewsArticleEntity {
  return NewsArticleEntityMapper.asEntity(this)
}

fun NewsArticleEntity.asDomain(): NewsArticle {
  return NewsArticleEntityMapper.asDomain(this)
}
