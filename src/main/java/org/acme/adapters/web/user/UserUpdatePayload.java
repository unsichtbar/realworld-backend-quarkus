package org.acme.adapters.web.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserUpdatePayload {

  private User user;

  @AllArgsConstructor
  @Data
  @NoArgsConstructor
  @Builder
  public static class User {
     private String email;
     private String password;
     private String username;
     private String bio;
     private String image;
  }
}