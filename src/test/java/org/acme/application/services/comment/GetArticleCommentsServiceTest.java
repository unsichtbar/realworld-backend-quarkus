package org.acme.application.services.comment;

import org.acme.application.model.Comment;
import org.acme.application.ports.out.LoadCommentPort;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetArticleCommentsServiceTest {

  @InjectMocks private GetArticleCommentsService sut;

  @Mock private LoadCommentPort loadCommentPort;

  @DisplayName("Loads comments for the requested article slug")
  @Test
  void loads_comments() {
    // Arrange
    String slug = "the-slug";
    Collection<Comment> comments = new ArrayList<>();
    comments.add(Comment.builder().build());
    Mockito.when(this.loadCommentPort.getComments(slug)).thenReturn(comments);
    // Act
    Collection<Comment> actual = sut.getComments(slug);
    // Assert
    Assertions.assertEquals(comments, actual);
  }
}
