package org.acme.adapters.persistence.article;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface ArticleRepository extends JpaRepository<ArticleJpaEntity, Integer> {
  Optional<ArticleJpaEntity> findBySlug(String slug);
}
