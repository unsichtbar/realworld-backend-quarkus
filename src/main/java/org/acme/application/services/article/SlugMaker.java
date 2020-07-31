package org.acme.application.services.article;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
class SlugMaker {

  public String createSlug(String title) {
    return title.replaceAll("\\s+", "-");
  }
}
