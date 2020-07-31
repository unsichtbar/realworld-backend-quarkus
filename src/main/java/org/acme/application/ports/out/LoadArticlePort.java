package org.acme.application.ports.out;

import org.acme.application.model.Article;
import java.util.Optional;

public interface LoadArticlePort {
  Optional<Article> findArticle(String slug);

  Optional<Article> findArticleById(Integer id);
}
