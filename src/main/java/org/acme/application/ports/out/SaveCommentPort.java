package org.acme.application.ports.out;

import org.acme.application.model.Comment;

public interface SaveCommentPort {
  Comment save(Comment input);
}
