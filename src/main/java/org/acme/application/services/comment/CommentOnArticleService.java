package org.acme.application.services.comment;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.acme.application.exceptions.ArticleNotFoundException;
import org.acme.application.model.Article;
import org.acme.application.model.Comment;
import org.acme.application.model.CommentId;
import org.acme.application.model.Profile;
import org.acme.application.ports.in.CommentOnArticleUseCase;
import org.acme.application.ports.in.GetProfileQuery;
import org.acme.application.ports.out.LoadArticlePort;
import org.acme.application.ports.out.SaveCommentPort;

@ApplicationScoped
@AllArgsConstructor
class CommentOnArticleService implements CommentOnArticleUseCase {

  private GetProfileQuery getProfileQuery;
  private SaveCommentPort saveCommentPort;
  private LoadArticlePort loadArticlePort;

  @Override
  public Comment publishComment(PublishCommentCommand input) {

    Profile author =
        getProfileQuery.getProfile(input.getCommentAuthor().getUsername(), Optional.empty());
    Article article =
        loadArticlePort
            .findArticle(input.getArticleSlug())
            .orElseThrow(ArticleNotFoundException::new);
    Comment newComment =
        Comment.builder()
            .body(input.getBody())
            .author(author)
            .id(CommentId.builder().build())
            .articleId(article.getId())
            .build();
    return this.saveCommentPort.save(newComment);
  }
}
