package org.acme.application.ports.in;

import javax.security.auth.login.FailedLoginException;
import org.acme.application.model.User;

public interface LoginUserUseCase {
  public User login(String email, String password) throws FailedLoginException;
}
