package org.acme.adapters.persistence.article;

import org.springframework.data.jpa.repository.JpaRepository;

interface ArticleFavoriteRepository extends JpaRepository<ArticleFavoriteJpaEntity, Integer> {

  Boolean existsByArticleIdAndUserId(Integer articleId, Integer userId);

  Long countByArticleId(Integer articleId);

  ArticleFavoriteJpaEntity findByArticleIdAndUserId(Integer articleId, Integer userId);
}
