package org.acme.application.ports.out;

import org.acme.application.model.User;
import java.util.Optional;

public interface GetUserPort {
  Optional<User> getUserByUsername(String username);

  Optional<User> getUserByEmail(String email);

  Optional<User> getUserById(Integer userId);
}
