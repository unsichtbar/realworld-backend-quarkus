package org.acme.application.ports.out;

public interface LoadArticleFavoritedPort {

  Boolean isArticleFavoritedBy(Integer article, Integer userId);
}
