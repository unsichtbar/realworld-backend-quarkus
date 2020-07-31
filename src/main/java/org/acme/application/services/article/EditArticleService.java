package org.acme.application.services.article;

import javax.enterprise.context.ApplicationScoped;

import org.acme.application.exceptions.ArticleAlreadyExistsException;
import org.acme.application.exceptions.ArticleNotFoundException;
import org.acme.application.model.Article;
import org.acme.application.ports.in.EditArticleUseCase;
import org.acme.application.ports.out.LoadArticlePort;
import org.acme.application.ports.out.UpdateArticlePort;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class EditArticleService implements EditArticleUseCase {

  private final LoadArticlePort loadArticlePort;
  private final UpdateArticlePort updateArticlePort;
  private final SlugMaker slugMaker;

  @Override
  public Article editArticle(EditArticleCommand draftArticle) {
    assert draftArticle.getBody() != null;
    assert draftArticle.getDescription() != null;
    assert draftArticle.getTitle() != null;

    Article article =
        this.loadArticlePort
            .findArticleById(draftArticle.getId())
            .orElseThrow(ArticleNotFoundException::new);

    if (!"".equals(draftArticle.getTitle())) {
      article.setTitle(draftArticle.getTitle());
      String newSlug = this.slugMaker.createSlug(draftArticle.getTitle());
      loadArticlePort
          .findArticle(newSlug)
          .ifPresent(
              (collision) -> {
                throw new ArticleAlreadyExistsException();
              });
      article.setSlug(newSlug);
    }
    if (!"".equals(draftArticle.getDescription())) {
      article.setDescription(draftArticle.getDescription());
    }
    if (!"".equals(draftArticle.getBody())) {
      article.setBody(draftArticle.getBody());
    }

    return this.updateArticlePort.updateArticle(article);
  }
}
