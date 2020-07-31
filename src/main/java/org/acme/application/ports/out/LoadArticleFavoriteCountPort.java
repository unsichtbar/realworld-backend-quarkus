package org.acme.application.ports.out;

public interface LoadArticleFavoriteCountPort {

  Integer getFavoriteCount(Integer articleId);
}
