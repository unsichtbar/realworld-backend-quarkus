package org.acme.application.ports.in;

import org.acme.application.model.Article;
import org.acme.application.model.User;

public interface FavoriteArticleUseCase {
  Article favoriteArticle(String slug, User requester);

  Article unfavoriteArticle(String slug, User requester);
}
