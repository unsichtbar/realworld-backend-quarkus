package org.acme.application.ports.out;

import org.acme.application.model.Article;
import java.util.Collection;
import java.util.Optional;

public interface LoadRecentArticlesPort {

  Collection<Article> loadRecentArticles(
      Optional<String> tags,
      Optional<String> author,
      Optional<String> favorited,
      Integer limit,
      Integer offset);
}
