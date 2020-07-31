package org.acme.application.services.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.acme.application.exceptions.ArticleNotFoundException;
import org.acme.application.model.Article;
import org.acme.application.model.Comment;
import org.acme.application.model.Profile;
import org.acme.application.model.User;
import org.acme.application.ports.in.CommentOnArticleUseCase;
import org.acme.application.ports.in.GetProfileQuery;
import org.acme.application.ports.out.LoadArticlePort;
import org.acme.application.ports.out.SaveCommentPort;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentOnArticleServiceTest {
  @InjectMocks private CommentOnArticleService sut;
  @Mock private SaveCommentPort saveCommentPort;
  @Mock private GetProfileQuery loadProfileQuery;
  @Mock private LoadArticlePort loadArticlePort;

  @Test
  @DisplayName("a user can comment on an article")
  void a_user_can_comment_on_an_article() {
    // Arrange
    String slug = "article-slug";
    String body = "comment body";
    Integer articleId = 1234;
    String author = "author";
    CommentOnArticleUseCase.PublishCommentCommand input =
        CommentOnArticleUseCase.PublishCommentCommand.builder()
            .articleSlug(slug)
            .body(body)
            .commentAuthor(User.builder().username(author).build())
            .build();

    Mockito.when(loadProfileQuery.getProfile(author, Optional.empty()))
        .thenReturn(Profile.builder().username(author).build());
    Mockito.when(loadArticlePort.findArticle(slug))
        .thenReturn(Optional.of(Article.builder().id(articleId).slug(slug).build()));
    Mockito.when(saveCommentPort.save(Mockito.any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
    // Act
    Comment actual = sut.publishComment(input);
    // Assert
    Assertions.assertEquals(author, actual.getAuthor().getUsername());
    Assertions.assertEquals(body, actual.getBody());
    Assertions.assertFalse(actual.getId().toString().isEmpty());
  }

  @Test
  @DisplayName("Comments can only be craeted for articles that exist")
  void comments_only_created_for_article_that_exist() {
    // Arrange
    var article = "article-slug";
    var input =
        CommentOnArticleUseCase.PublishCommentCommand.builder()
            .body("fake body")
            .commentAuthor(User.builder().build())
            .articleSlug(article)
            .build();
    Mockito.when(this.loadProfileQuery.getProfile(Mockito.any(), Mockito.any())).thenReturn(null);
    Mockito.when(this.loadArticlePort.findArticle(article)).thenReturn(Optional.empty());
    // Act & Assert
    Assertions.assertThrows(ArticleNotFoundException.class, () -> sut.publishComment(input));
  }
}
