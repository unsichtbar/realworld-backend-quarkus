package org.acme.adapters.persistence.comment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.acme.adapters.persistence.user.UserRepository;
import org.acme.application.model.Comment;
import org.acme.application.model.CommentId;
import org.acme.application.ports.out.DeleteCommentPort;
import org.acme.application.ports.out.LoadCommentPort;
import org.acme.application.ports.out.LoadProfilePort;
import org.acme.application.ports.out.SaveCommentPort;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class CommentPersistenceAdapter implements DeleteCommentPort, LoadCommentPort, SaveCommentPort {

  private CommentJpaRepository repository;
  private CommentMapper mapper;
  private LoadProfilePort profilePort;
  private UserRepository userRepository;

  @Override
  public Comment delete(CommentId input) {
    Optional<CommentJpaEntity> entity = this.repository.findById(input.getId().toString());
    if (entity.isEmpty()) return Comment.builder().build();
    Comment comment = mapper.mapEntityToDomain(entity.get());
    fillAuthor(entity, comment);
    this.repository.deleteById(input.getId().toString());
    return comment;
  }

  private void fillAuthor(Optional<CommentJpaEntity> entity, Comment comment) {
    comment.setAuthor(
        profilePort
            .loadProfile(userRepository.findById(entity.get().getAuthorId()).get().getUsername())
            .get());
  }

  @Override
  public Optional<Comment> getComment(CommentId id) {
    Optional<CommentJpaEntity> entity = this.repository.findById(id.getId().toString());
    if (entity.isEmpty()) return Optional.empty();
    Comment comment = this.mapper.mapEntityToDomain(entity.get());
    fillAuthor(entity, comment);
    return Optional.of(comment);
  }

  @Override
  public Collection<Comment> getComments(String slug) {
    Map<CommentJpaEntity, Comment> comments = new HashMap<>();
    this.repository
        .findByArticleSlug(slug)
        .forEach(
            (entity) -> {
              comments.put(entity, mapper.mapEntityToDomain(entity));
            });
    comments.forEach(
        (entity, comment) -> {
          fillAuthor(Optional.of(entity), comment);
        });
    return comments.values();
  }

  @Override
  public Comment save(Comment input) {
    return input; // TODO actually save this
    //return this.repository.save(this.mapper.);
  }
}
