package org.acme.application.ports.out;

import org.acme.application.model.Comment;
import org.acme.application.model.CommentId;

public interface DeleteCommentPort {

  Comment delete(CommentId input);
}
