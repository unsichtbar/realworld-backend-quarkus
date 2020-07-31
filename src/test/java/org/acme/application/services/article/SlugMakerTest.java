package org.acme.application.services.article;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Slug Maker")
class SlugMakerTest {

  SlugMaker sut = new SlugMaker();

  @Test
  @DisplayName("Replaces whitespace with dashes")
  void replaces_whitespace_with_dashes() {
    // Arrange
    String str = "hello world";
    // Act
    String actual = sut.createSlug(str);
    // Assert
    Assertions.assertEquals("hello-world", actual);
  }
}
