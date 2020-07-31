package org.acme.application.ports.in;

import org.acme.application.model.Article;
import org.acme.application.model.User;
import java.util.List;
import java.util.Optional;

public interface GetArticleQuery {

  Article getArticle(String slug, User requester);

  Article getArticle(String slug);

  List<Article> getRecentArticles(
      Optional<String> tag,
      Optional<String> author,
      Optional<String> favorited,
      Optional<Integer> limit,
      Optional<Integer> offset,
      Optional<User> user);
}
