package org.acme.application.services.article;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.acme.application.exceptions.ArticleNotFoundException;
import org.acme.application.model.Article;
import org.acme.application.model.Profile;
import org.acme.application.model.User;
import org.acme.application.ports.in.GetArticleQuery;
import org.acme.application.ports.in.GetProfileQuery;
import org.acme.application.ports.out.LoadArticleFavoriteCountPort;
import org.acme.application.ports.out.LoadArticleFavoritedPort;
import org.acme.application.ports.out.LoadArticlePort;
import org.acme.application.ports.out.LoadRecentArticlesPort;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class GetArticleQueryService implements GetArticleQuery {

  private LoadArticlePort loadArticlePort;
  private GetProfileQuery getProfileQuery;
  private LoadArticleFavoritedPort loadArticleFavoritedPort;
  private LoadArticleFavoriteCountPort loadArticleFavoriteCountPort;
  private LoadRecentArticlesPort loadRecentArticlesPort;

  @Override
  public Article getArticle(String slug) {
    return this.getArticle(slug, Optional.empty());
  }

  @Override
  public Article getArticle(String slug, User requester) {
    return this.getArticle(slug, Optional.ofNullable(requester));
  }

  private Article getArticle(String slug, Optional<User> requester) {

    Article article =
        this.loadArticlePort.findArticle(slug).orElseThrow(ArticleNotFoundException::new);

    Profile profile = getProfileQuery.getProfile(article.getAuthor().getUsername(), requester);
    fillArticleFavoriteInfo(article, requester);
    article.setFavoritesCount(this.loadArticleFavoriteCountPort.getFavoriteCount(article.getId()));
    article.setAuthor(profile);
    return article;
  }

  @Override
  public List<Article> getRecentArticles(
      Optional<String> tag,
      Optional<String> author,
      Optional<String> favorited,
      Optional<Integer> limit,
      Optional<Integer> offset,
      Optional<User> user) {
    Integer DEFAULT_LIMIT = 20;
    Integer DEFAULT_OFFSET = 0;
    Collection<Article> articles =
        this.loadRecentArticlesPort.loadRecentArticles(
            tag, author, favorited, limit.orElse(DEFAULT_LIMIT), offset.orElse(DEFAULT_OFFSET));
    fillArticleFavoriteInfo(articles, user);

    return new ArrayList<>(articles);
  }

  private void fillArticleFavoriteInfo(Article article, Optional<User> user) {
    if (user.isPresent())
      article.setFavorited(
          loadArticleFavoritedPort.isArticleFavoritedBy(article.getId(), user.get().getId()));
    else article.setFavorited(false);
  }

  private void fillArticleFavoriteInfo(Collection<Article> articles, Optional<User> user) {
    articles.forEach(article -> fillArticleFavoriteInfo(article, user));
  }
}
