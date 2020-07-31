package org.acme.application.services.article;

import javax.enterprise.context.ApplicationScoped;

import org.acme.application.exceptions.ArticleAlreadyExistsException;
import org.acme.application.model.Article;
import org.acme.application.model.Profile;
import org.acme.application.ports.in.PublishArticleUseCase;
import org.acme.application.ports.out.LoadArticlePort;
import org.acme.application.ports.out.SaveArticlePort;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class PublishArticleService implements PublishArticleUseCase {
  private final LoadArticlePort loadArticlePort;
  private final SaveArticlePort saveArticlePort;
  private final SlugMaker slugMaker;

  @Override
  public Article publishArticle(PublishArticleCommand articleToPublish) {
    assert articleToPublish != null;
    assert articleToPublish.getBody() != null;
    assert articleToPublish.getDescription() != null;
    assert articleToPublish.getTitle() != null;
    assert articleToPublish.getPublisher() != null;
    loadArticlePort
        .findArticle(slugMaker.createSlug(articleToPublish.getTitle()))
        .ifPresent(
            (article) -> {
              throw new ArticleAlreadyExistsException();
            });
    Article article =
        Article.builder()
            .author(
                Profile.builder().username(articleToPublish.getPublisher().getUsername()).build())
            .body(articleToPublish.getBody())
            .description(articleToPublish.getDescription())
            .slug(slugMaker.createSlug(articleToPublish.getTitle()))
            .tags(articleToPublish.getTagList())
            .title(articleToPublish.getTitle())
            .favorited(false)
            .favoritesCount(0)
            .build();
    Article created = saveArticlePort.createArticle(article);
    return created;
  }
}
