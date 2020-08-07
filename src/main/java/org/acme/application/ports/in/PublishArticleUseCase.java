package org.acme.application.ports.in;

import java.util.List;
import lombok.*;
import org.acme.application.exceptions.ArticleAlreadyExistsException;
import org.acme.application.model.Article;
import org.acme.application.model.User;

public interface PublishArticleUseCase {

  Article publishArticle(PublishArticleCommand article) throws ArticleAlreadyExistsException;

  @Data
  @NoArgsConstructor
  @Builder
  @AllArgsConstructor
  @RequiredArgsConstructor
  class PublishArticleCommand {
    @NonNull private String title;
    @NonNull private String description;
    @NonNull private String body;
    private List<String> tagList;
    @NonNull private User publisher;
  }
}
