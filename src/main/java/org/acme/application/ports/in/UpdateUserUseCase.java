package org.acme.application.ports.in;

import org.acme.application.model.User;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

public interface UpdateUserUseCase {
  User updateUser(UpdateUserCommand input);

  @Data
  @Builder
  class UpdateUserCommand {
    @NonNull private Integer user;

     private String email;
     private String username;
     private String password;
     private String image;
     private String bio;
  }
}
