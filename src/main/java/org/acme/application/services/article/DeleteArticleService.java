package org.acme.application.services.article;

import javax.enterprise.context.ApplicationScoped;

import org.acme.application.exceptions.ArticleNotFoundException;
import org.acme.application.model.Article;
import org.acme.application.ports.in.DeleteArticleUseCase;
import org.acme.application.ports.out.DeleteArticlePort;
import org.acme.application.ports.out.LoadArticlePort;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
class DeleteArticleService implements DeleteArticleUseCase {

  private final LoadArticlePort loadArticlePort;
  private final DeleteArticlePort deleteArticlePort;

  @Override
  public Boolean deleteArticleBySlug(String slug) {
    Article article = loadArticlePort.findArticle(slug).orElseThrow(ArticleNotFoundException::new);
    deleteArticlePort.deleteArticleById(article.getId());
    return true;
  }
}
