package org.acme.application.ports.out;

import org.acme.application.model.Article;

public interface SaveArticlePort {
  Article createArticle(Article article);
}
