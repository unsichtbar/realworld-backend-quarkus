package org.acme.adapters.persistence.article;

import java.util.Collections;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.acme.adapters.persistence.tag.TagJpaEntity;
import org.acme.application.model.Article;
import org.acme.application.model.Profile;

@ApplicationScoped
class ArticleMapper {
  Article mapJpaToDomain(ArticleJpaEntity jpa, Long favoritesCount) {
    return Article.builder()
        .title(jpa.getTitle())
        .tags(
            jpa.getTags() == null
                ? Collections.emptyList()
                : jpa.getTags().stream().map(tag -> tag.getTag()).collect(Collectors.toList()))
        .slug(jpa.getSlug())
        .description(jpa.getDescription())
        .body(jpa.getBody())
        .author(
            Profile.builder()
                .username(jpa.getAuthor().getUsername())
                .following(false)
                .image(jpa.getAuthor().getImage())
                .bio(jpa.getAuthor().getBio())
                .build())
        .id(jpa.getId())
        .favorited(false)
        .favoritesCount(favoritesCount.intValue())
        .createdAt(jpa.getCreatedAt())
        .updatedAt(jpa.getUpdatedAt())
        .build();
  }

  ArticleJpaEntity mapDomainToJpa(Article article) {

    return ArticleJpaEntity.builder()
        //                .author(UserJpaEntity.builder()
        //                        .id(author.getId())
        //                        .email(author.getEmail())
        //                        .bio(author.getBio())
        //                        .image(author.getImage())
        //                        .username(author.getUsername())
        //                        .password(author.getPassword())
        //                        .build())
        .body(article.getBody())
        .description(article.getDescription())
        .id(article.getId())
        .slug(article.getSlug())
        .title(article.getTitle())
        .tags(
            article.getTags() == null
                ? Collections.emptyList()
                : article.getTags().stream()
                    .map(str -> TagJpaEntity.builder().tag(str).build())
                    .collect(Collectors.toList()))
        .build();
  }
}
