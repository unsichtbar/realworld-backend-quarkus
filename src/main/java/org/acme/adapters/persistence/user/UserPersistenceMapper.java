package org.acme.adapters.persistence.user;

import javax.enterprise.context.ApplicationScoped;

import org.acme.application.model.User;

@ApplicationScoped
public class UserPersistenceMapper {
  public User mapJpaEntityToDomain(UserJpaEntity user) {
    return User.builder()
        .bio(user.getBio())
        .email(user.getEmail())
        .image(user.getImage())
        .password(user.getPassword())
        .username(user.getUsername())
        .id(user.getId())
        .build();
  }

  public UserJpaEntity mapDomainToJpaEntity(User user) {
    return UserJpaEntity.builder()
        .bio(user.getBio())
        .email(user.getEmail())
        .image(user.getImage())
        .password(user.getPassword())
        .username(user.getUsername())
        .id(user.getId())
        .build();
  }
}
