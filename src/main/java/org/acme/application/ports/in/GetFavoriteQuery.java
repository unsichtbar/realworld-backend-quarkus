package org.acme.application.ports.in;

public interface GetFavoriteQuery {
  Boolean hasFavorited(Integer userId, Integer articleId);
}
