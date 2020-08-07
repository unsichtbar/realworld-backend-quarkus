package org.acme.application.services.article;

import java.util.Optional;
import org.acme.application.exceptions.ArticleAlreadyExistsException;
import org.acme.application.model.Article;
import org.acme.application.model.Profile;
import org.acme.application.model.User;
import org.acme.application.ports.in.PublishArticleUseCase;
import org.acme.application.ports.out.LoadArticlePort;
import org.acme.application.ports.out.SaveArticlePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Publish Article Service")
class PublishArticleServiceTest {

  @InjectMocks private PublishArticleService sut;

  @Mock private LoadArticlePort loadArticlePort;
  @Mock private SaveArticlePort saveArticlePort;
  @Mock private SlugMaker slugMaker;

  @DisplayName("publishes article")
  @Test
  void publishes_article() {
    // Arrange
    User publisher = User.builder().username("user1").build();
    PublishArticleUseCase.PublishArticleCommand command =
        new PublishArticleUseCase.PublishArticleCommand("title", "description", "body", publisher);
    Mockito.when(slugMaker.createSlug("title")).thenReturn("title");
    Mockito.when(loadArticlePort.findArticle("title")).thenReturn(Optional.empty());
    Profile author = Profile.builder().username("user1").build();
    Article article =
        Article.builder()
            .author(author)
            .body("body")
            .description("description")
            .slug("title")
            .tags(null)
            .title("title")
            .favoritesCount(0)
            .favorited(false)
            .build();
    Mockito.when(saveArticlePort.createArticle(article)).thenReturn(article);
    // Act
    Article actual = this.sut.publishArticle(command);
    // Assert
    Mockito.verify(saveArticlePort).createArticle(article);
    Assertions.assertEquals(false, actual.getFavorited());
    Assertions.assertEquals(0, actual.getFavoritesCount());
  }

  @DisplayName("fails validation if body is not present")
  @Test
  void body_required() {
    // Arrange
    PublishArticleUseCase.PublishArticleCommand command =
        new PublishArticleUseCase.PublishArticleCommand();
    // Act
    Assertions.assertThrows(AssertionError.class, () -> this.sut.publishArticle(command));
  }

  @DisplayName("fails validation if description is not present")
  @Test
  void description_required() {
    // Arrange
    PublishArticleUseCase.PublishArticleCommand command =
        new PublishArticleUseCase.PublishArticleCommand();
    command.setBody("body");
    // Act
    Assertions.assertThrows(AssertionError.class, () -> this.sut.publishArticle(command));
  }

  @DisplayName("fails validation if title is not present")
  @Test
  void title_required() {
    // Arrange
    PublishArticleUseCase.PublishArticleCommand command =
        new PublishArticleUseCase.PublishArticleCommand();
    command.setBody("body");
    command.setDescription("description");
    // Act
    Assertions.assertThrows(AssertionError.class, () -> this.sut.publishArticle(command));
  }

  @DisplayName("fails validation if author is not present")
  @Test
  void author_required() {
    // Arrange
    PublishArticleUseCase.PublishArticleCommand command =
        new PublishArticleUseCase.PublishArticleCommand();
    command.setBody("body");
    command.setDescription("description");
    command.setTitle("title");
    // Act
    Assertions.assertThrows(AssertionError.class, () -> this.sut.publishArticle(command));
  }

  @DisplayName("does not create duplicate articles with the same slug")
  @Test
  void no_duplicates_on_slug() {

    // Arrange
    User publisher = User.builder().username("user1").build();
    PublishArticleUseCase.PublishArticleCommand command =
        new PublishArticleUseCase.PublishArticleCommand("title", "description", "body", publisher);
    Mockito.when(slugMaker.createSlug("title")).thenReturn("title");

    Profile author = Profile.builder().username("user1").build();
    Article article =
        Article.builder()
            .author(author)
            .body("body")
            .description("description")
            .slug("title")
            .tags(null)
            .title("title")
            .favoritesCount(0)
            .favorited(false)
            .build();
    Mockito.when(loadArticlePort.findArticle("title")).thenReturn(Optional.of(article));
    // Act & Assert

    Assertions.assertThrows(ArticleAlreadyExistsException.class, () -> sut.publishArticle(command));
  }
}
