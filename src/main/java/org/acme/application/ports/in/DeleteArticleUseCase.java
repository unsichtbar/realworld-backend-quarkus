package org.acme.application.ports.in;

public interface DeleteArticleUseCase {

  Boolean deleteArticleBySlug(String slug);
}
