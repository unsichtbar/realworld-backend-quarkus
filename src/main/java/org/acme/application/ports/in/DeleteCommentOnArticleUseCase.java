package org.acme.application.ports.in;

import org.acme.application.exceptions.ArticleNotFoundException;
import org.acme.application.exceptions.CommentNotFoundException;
import org.acme.application.model.Comment;
import org.acme.application.model.CommentId;
import org.acme.application.model.User;
import lombok.Builder;
import lombok.Data;

public interface DeleteCommentOnArticleUseCase {

  Comment delete(DeleteCommentCommand input)
      throws CommentNotFoundException, ArticleNotFoundException;

  @Data
  @Builder
  class DeleteCommentCommand {
    CommentId commentId;
    String slug;
    User requester;
  }
}
