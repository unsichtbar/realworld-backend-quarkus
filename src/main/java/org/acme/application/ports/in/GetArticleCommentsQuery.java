package org.acme.application.ports.in;

import org.acme.application.model.Comment;
import org.acme.application.model.User;
import java.util.Collection;

public interface GetArticleCommentsQuery {

  Collection<Comment> getComments(String slug);

  Collection<Comment> getComments(String slug, User requester);
}
