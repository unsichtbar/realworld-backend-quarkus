package org.acme.application.services.article;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.acme.application.exceptions.ArticleNotFoundException;
import org.acme.application.model.Article;
import org.acme.application.ports.out.DeleteArticlePort;
import org.acme.application.ports.out.LoadArticlePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
@DisplayName("Delete Article Service")
class DeleteArticleServiceTest {
  @InjectMocks DeleteArticleService sut;
  @Mock LoadArticlePort loadArticlePort;
  @Mock DeleteArticlePort deleteArticlePort;

  @Test
  @DisplayName("deletes an article")
  void delete_article() {
    // Arrange
    String slug = "slug";
    Article article = Article.builder().slug(slug).id(1234).build();
    when(loadArticlePort.findArticle(slug)).thenReturn(Optional.of(article));
    // Act
    Boolean actual = sut.deleteArticleBySlug(slug);
    // Assert
    verify(deleteArticlePort).deleteArticleById(article.getId());
    Assertions.assertEquals(true, actual);
  }

  @Test
  @DisplayName("article must exist in order to be deleted")
  void article_must_exist() {
    // Arrange
    String slug = "slug";
    when(loadArticlePort.findArticle(slug)).thenReturn(Optional.empty());
    // Act
    Assertions.assertThrows(ArticleNotFoundException.class, () -> sut.deleteArticleBySlug(slug));
  }
}
