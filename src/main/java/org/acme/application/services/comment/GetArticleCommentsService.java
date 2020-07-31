package org.acme.application.services.comment;

import java.util.Collection;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.acme.application.model.Comment;
import org.acme.application.model.User;
import org.acme.application.ports.in.GetArticleCommentsQuery;
import org.acme.application.ports.in.GetProfileQuery;
import org.acme.application.ports.out.LoadCommentPort;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class GetArticleCommentsService implements GetArticleCommentsQuery {

  private LoadCommentPort loadCommentPort;
  private GetProfileQuery getProfileQuery;

  @Override
  public Collection<Comment> getComments(String slug) {
    return this.loadCommentPort.getComments(slug);
  }

  @Override
  public Collection<Comment> getComments(String slug, User requester) {
    var comments = this.getComments(slug);
    comments.forEach(
        comment -> {
          comment.setAuthor(
              getProfileQuery.getProfile(
                  comment.getAuthor().getUsername(), Optional.of(requester)));
        });
    return comments;
  }
}
