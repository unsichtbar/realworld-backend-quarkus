package org.acme.application.ports.in;

import org.acme.application.exceptions.ArticleNotFoundException;
import org.acme.application.exceptions.UserNotFoundException;
import org.acme.application.model.Comment;
import org.acme.application.model.User;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

public interface CommentOnArticleUseCase {

  Comment publishComment(PublishCommentCommand input)
      throws UserNotFoundException, ArticleNotFoundException;

  @Data
  @Builder
  class PublishCommentCommand {

    @NonNull private String body;
    @NonNull private String articleSlug;
    @NonNull private User commentAuthor;
  }
}
