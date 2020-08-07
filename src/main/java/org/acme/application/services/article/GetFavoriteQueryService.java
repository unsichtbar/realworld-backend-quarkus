package org.acme.application.services.article;

import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.acme.application.ports.in.GetFavoriteQuery;
import org.acme.application.ports.out.LoadArticleFavoritedPort;

@ApplicationScoped
@AllArgsConstructor
class GetFavoriteQueryService implements GetFavoriteQuery {

  private LoadArticleFavoritedPort loadArticleFavoritedPort;

  @Override
  public Boolean hasFavorited(Integer userId, Integer articleId) {
    return this.loadArticleFavoritedPort.isArticleFavoritedBy(articleId, userId);
  }
}
