package org.acme.adapters.web.user;

import javax.enterprise.context.ApplicationScoped;
import org.acme.application.model.User;

@ApplicationScoped
class UserWebMapper {
  public GetUserResponse mapUserToResponse(User user) {
    return GetUserResponse.builder()
        .user(
            GetUserResponse.User.builder()
                .bio(user.getBio())
                .email(user.getEmail())
                .image(user.getImage())
                .username(user.getUsername())
                .token(user.getToken())
                .build())
        .build();
  }

  public User mapResponseToUser(UserUpdatePayload res) {
    return User.builder()
        .username(res.getUser().getUsername())
        .image(res.getUser().getImage())
        .email(res.getUser().getEmail())
        .bio(res.getUser().getBio())
        .password(res.getUser().getPassword())
        .build();
  }
}
