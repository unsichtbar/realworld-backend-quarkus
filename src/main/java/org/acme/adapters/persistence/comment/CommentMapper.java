package org.acme.adapters.persistence.comment;

import javax.enterprise.context.ApplicationScoped;

import org.acme.application.model.Comment;
import org.acme.application.model.CommentId;

@ApplicationScoped
class CommentMapper {

  public Comment mapEntityToDomain(CommentJpaEntity entity) {
    return Comment.builder()
        .articleId(entity.getArticle().getId())
        .id(CommentId.builder().id(entity.getId()).build())
        .body(entity.getBody())
        .createAt(entity.getCreatedAt())
        .updated(entity.getUpdatedAt())
        .build();
  }
}
