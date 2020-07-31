package org.acme.application.ports.out;

import org.acme.application.model.Article;

public interface DeleteArticlePort {
  Article deleteArticleById(Integer id);
}
