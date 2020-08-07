package org.acme.application.services.article;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.acme.application.exceptions.ArticleNotFoundException;
import org.acme.application.model.Article;
import org.acme.application.ports.in.EditArticleUseCase;
import org.acme.application.ports.out.LoadArticlePort;
import org.acme.application.ports.out.UpdateArticlePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
@DisplayName("Edit Article Service")
class EditArticleServiceTest {

  @InjectMocks EditArticleService sut;
  @Mock private LoadArticlePort loadArticlePort;
  @Mock private UpdateArticlePort updateArticlePort;
  @Mock private SlugMaker slugMaker;

  @Test
  @DisplayName("article should be updated with payload article data")
  void update_article() {
    // Arrange
    Integer articleId = 1234;
    EditArticleUseCase.EditArticleCommand payload =
        EditArticleUseCase.EditArticleCommand.builder()
            .body("new body")
            .id(articleId)
            .description("new description")
            .title("new title")
            .build();
    Optional<Article> existingArticle = Optional.of(Article.builder().id(articleId).build());
    when(loadArticlePort.findArticleById(articleId)).thenReturn(existingArticle);
    when(slugMaker.createSlug("new title")).thenReturn("new-title");

    when(updateArticlePort.updateArticle(Mockito.any(Article.class)))
        .thenAnswer(
            new Answer() {
              @Override
              public Article answer(InvocationOnMock invocation) throws Throwable {
                return invocation.getArgument(0);
              }
            });
    // Act
    Article actual = sut.editArticle(payload);
    // Assert
    assertEquals("new title", actual.getTitle());
    assertEquals("new-title", actual.getSlug());
    assertEquals("new body", actual.getBody());
    assertEquals("new description", actual.getDescription());
  }

  @Test
  @DisplayName("article to update must exist")
  void update_article_must_exist() {
    // Arrange
    Integer articleId = 1234;
    EditArticleUseCase.EditArticleCommand payload =
        EditArticleUseCase.EditArticleCommand.builder()
            .body("new body")
            .id(articleId)
            .description("new description")
            .title("new title")
            .build();
    when(this.loadArticlePort.findArticleById(articleId)).thenReturn(Optional.empty());
    // Act
    Assertions.assertThrows(ArticleNotFoundException.class, () -> sut.editArticle(payload));
  }
}
