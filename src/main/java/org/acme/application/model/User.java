package org.acme.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class User {
  private Integer id;
  private String email;
  private String username;
  private String password;
  private String token;
  private String bio;
  private String image;
}
