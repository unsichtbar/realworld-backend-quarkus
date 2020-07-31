package org.acme.users;

import java.util.Optional;

import org.acme.models.User;

public interface UserRepository {

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);
  
    Optional<User> getUserById(Integer userId);
}
