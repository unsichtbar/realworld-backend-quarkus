package org.acme.application.ports.out;

import org.acme.application.model.User;

public interface SaveUserPort {
  User saveUser(User user);
}
