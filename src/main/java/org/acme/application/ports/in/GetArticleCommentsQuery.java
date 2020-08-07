package org.acme.application.ports.in;

import java.util.Collection;
import org.acme.application.model.Comment;
import org.acme.application.model.User;

public interface GetArticleCommentsQuery {

  Collection<Comment> getComments(String slug);

  Collection<Comment> getComments(String slug, User requester);
}
