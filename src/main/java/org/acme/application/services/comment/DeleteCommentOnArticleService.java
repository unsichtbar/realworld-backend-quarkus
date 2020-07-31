package org.acme.application.services.comment;

import javax.enterprise.context.ApplicationScoped;

import org.acme.application.exceptions.CommentNotFoundException;
import org.acme.application.model.Comment;
import org.acme.application.ports.in.DeleteCommentOnArticleUseCase;
import org.acme.application.ports.in.GetArticleQuery;
import org.acme.application.ports.out.DeleteCommentPort;
import org.acme.application.ports.out.LoadCommentPort;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class DeleteCommentOnArticleService implements DeleteCommentOnArticleUseCase {

  private LoadCommentPort loadCommentPort;
  private GetArticleQuery getArticleQuery;
  private DeleteCommentPort deleteCommentPort;

  @Override
  public Comment delete(DeleteCommentCommand input) {

    Comment existingComment =
        this.loadCommentPort
            .getComment(input.getCommentId())
            .orElseThrow(CommentNotFoundException::new);
    this.getArticleQuery.getArticle(input.getSlug(), input.getRequester());

    this.deleteCommentPort.delete(input.getCommentId());
    return existingComment;
  }
}
