package org.acme.application.ports.out;

import org.acme.application.model.User;

public interface UpdateUserPort {

  User save(User payload);
}
