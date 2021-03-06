package org.acme.application.ports.in;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.acme.application.exceptions.ArticleNotFoundException;
import org.acme.application.model.Article;

public interface EditArticleUseCase {

  Article editArticle(EditArticleCommand draftArticle) throws ArticleNotFoundException;

  @Data
  @Builder
  class EditArticleCommand {
    @NonNull private Integer id;

    private String title;
    private String description;
    private String body;
  }
}
