package com.olehsh.newsapp.database.entity.mappers

import com.olehsh.newsapp.database.entity.RecentNewsSearchEntity
import com.olehsh.newsapp.database.entity.RecentSearchArticleSourceEntity
import com.olehsh.newsapp.model.ArticleSource
import com.olehsh.newsapp.model.NewsArticle

object RecentNewsSearchEntityMapper : EntityMapper<NewsArticle, RecentNewsSearchEntity> {

  override fun asEntity(domain: NewsArticle): RecentNewsSearchEntity {
    return RecentNewsSearchEntity(
      author = domain.author,
      content = domain.content,
      description = domain.description,
      publishedAt = domain.publishedAt,
      source = RecentSearchArticleSourceEntity(domain.source.id, domain.source.name.orEmpty()),
      title = domain.title,
      url = domain.url,
      imageUrl = domain.imageUrl,
    )
  }

  override fun asDomain(entity: RecentNewsSearchEntity): NewsArticle {
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

fun NewsArticle.asSearchEntity(): RecentNewsSearchEntity {
  return RecentNewsSearchEntityMapper.asEntity(this)
}

fun RecentNewsSearchEntity.asDomain(): NewsArticle {
  return RecentNewsSearchEntityMapper.asDomain(this)
}
