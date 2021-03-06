package org.acme.application.ports.out;

import java.util.Optional;
import org.acme.application.model.User;

public interface GetUserPort {
  Optional<User> getUserByUsername(String username);

  Optional<User> getUserByEmail(String email);

  Optional<User> getUserById(Integer userId);
}
