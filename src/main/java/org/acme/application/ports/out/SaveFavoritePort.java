package org.acme.application.ports.out;

import org.acme.application.model.Article;

public interface SaveFavoritePort {

  Article addFavorite(Integer articleId, Integer userId);

  Article removeFavorite(Integer articleId, Integer userId);
}
