package org.acme.application.ports.out;

import java.util.Collection;
import java.util.Optional;
import org.acme.application.model.Comment;
import org.acme.application.model.CommentId;

public interface LoadCommentPort {
  Optional<Comment> getComment(CommentId id);

  Collection<Comment> getComments(String slug);
}
