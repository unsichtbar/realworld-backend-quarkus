package org.acme.application.services.article;

import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.acme.application.model.Article;
import org.acme.application.model.User;
import org.acme.application.ports.in.FavoriteArticleUseCase;
import org.acme.application.ports.in.GetArticleQuery;
import org.acme.application.ports.in.GetFavoriteQuery;
import org.acme.application.ports.out.SaveFavoritePort;

@ApplicationScoped
@AllArgsConstructor
class FavoriteArticleService implements FavoriteArticleUseCase {

  private SaveFavoritePort saveFavoritePort;
  private GetArticleQuery getArticleQuery;
  private GetFavoriteQuery getFavoriteQuery;

  @Override
  public Article favoriteArticle(String slug, User requester) {
    Article found = this.getArticleQuery.getArticle(slug, requester);
    // if favorited don't favorite again, just return article
    if (getFavoriteQuery.hasFavorited(requester.getId(), found.getId())) {
      return this.getArticleQuery.getArticle(slug, requester);
    }
    this.saveFavoritePort.addFavorite(found.getId(), requester.getId());
    found.setFavoritesCount(found.getFavoritesCount() + 1);
    found.setFavorited(true);
    return found;
  }

  @Override
  public Article unfavoriteArticle(String slug, User requester) {
    Article found = this.getArticleQuery.getArticle(slug, requester);
    this.saveFavoritePort.removeFavorite(found.getId(), requester.getId());
    found.setFavorited(false);
    found.setFavoritesCount(found.getFavoritesCount() - 1);
    return found;
  }
}
