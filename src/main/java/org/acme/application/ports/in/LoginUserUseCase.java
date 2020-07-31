package org.acme.application.ports.in;

import org.acme.application.model.User;
import javax.security.auth.login.FailedLoginException;

public interface LoginUserUseCase {
  public User login(String email, String password) throws FailedLoginException;
}
