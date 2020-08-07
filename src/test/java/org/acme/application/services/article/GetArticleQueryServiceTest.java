package org.acme.application.services.article;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.acme.application.model.Article;
import org.acme.application.model.Profile;
import org.acme.application.model.User;
import org.acme.application.ports.in.GetProfileQuery;
import org.acme.application.ports.out.LoadArticleFavoriteCountPort;
import org.acme.application.ports.out.LoadArticleFavoritedPort;
import org.acme.application.ports.out.LoadArticlePort;
import org.acme.application.ports.out.LoadRecentArticlesPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Get Article Query Service")
class GetArticleQueryServiceTest {

  @InjectMocks GetArticleQueryService sut;
  @Mock private LoadArticlePort loadArticlePort;
  @Mock private GetProfileQuery getProfileQuery;
  @Mock private LoadArticleFavoritedPort loadArticleFavoritedPort;
  @Mock private LoadArticleFavoriteCountPort loadArticleFavoriteCountPort;
  @Mock private LoadRecentArticlesPort loadRecentArticlesPort;

  @Test
  @DisplayName("Get an article without a requester should return that the article is not favorited")
  void not_favorited_if_no_requester_present() {
    // Arrange
    String slug = "slug";
    String authorName = "author";
    Profile author = Profile.builder().username(authorName).build();
    Article article = Article.builder().author(author).id(1234).build();

    Mockito.when(this.loadArticlePort.findArticle(slug)).thenReturn(Optional.of(article));
    Mockito.when(this.getProfileQuery.getProfile(authorName, Optional.empty())).thenReturn(author);
    Mockito.when(this.loadArticleFavoriteCountPort.getFavoriteCount(Mockito.anyInt()))
        .thenReturn(0);
    // Act
    Article actual = this.sut.getArticle(slug);
    // Assert

    Assertions.assertEquals(article.getSlug(), actual.getSlug());
    Assertions.assertEquals(false, actual.getFavorited());
  }

  @Test
  @DisplayName("the found article should be favorited if the requester favorited it")
  void should_be_favorited_if_the_user_previously_favorited_the_article() {
    // Arrange
    String slug = "slug";
    String authorName = "author";
    Integer userId = 1111;
    Integer articleId = 1234;
    Profile author = Profile.builder().username(authorName).build();
    Article article = Article.builder().author(author).id(articleId).build();

    User requester = User.builder().id(userId).build();
    Mockito.when(this.loadArticlePort.findArticle(slug)).thenReturn(Optional.of(article));
    Mockito.when(this.getProfileQuery.getProfile(authorName, Optional.of(requester)))
        .thenReturn(author);
    Mockito.when(this.loadArticleFavoriteCountPort.getFavoriteCount(Mockito.anyInt()))
        .thenReturn(1);
    Mockito.when(this.loadArticleFavoritedPort.isArticleFavoritedBy(articleId, userId))
        .thenReturn(true);
    // Act
    Article actual = this.sut.getArticle(slug, requester);
    // Assert

    Assertions.assertEquals(article.getSlug(), actual.getSlug());
    Assertions.assertEquals(true, actual.getFavorited());
  }

  @Test
  @DisplayName("Retrieves recent articles with default limit and offset of 20 and 0 respectively")
  void retrieves_recent_articles() {
    // Arrange

    Mockito.when(
            this.loadRecentArticlesPort.loadRecentArticles(
                Optional.empty(), Optional.empty(), Optional.empty(), 20, 0))
        .thenReturn(Collections.emptyList());
    // Act
    List<Article> actual =
        sut.getRecentArticles(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty());
    // Assert
    Assertions.assertTrue(actual.isEmpty());
  }
}
