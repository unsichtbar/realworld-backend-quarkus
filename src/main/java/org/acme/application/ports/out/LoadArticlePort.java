package org.acme.application.ports.out;

import java.util.Optional;
import org.acme.application.model.Article;

public interface LoadArticlePort {
  Optional<Article> findArticle(String slug);

  Optional<Article> findArticleById(Integer id);
}
