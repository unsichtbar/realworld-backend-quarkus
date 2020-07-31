package org.acme.application.ports.out;

import org.acme.application.model.Article;

public interface UpdateArticlePort {
  Article updateArticle(Article draft);
}
