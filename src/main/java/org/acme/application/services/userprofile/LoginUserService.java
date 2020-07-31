package org.acme.application.services.userprofile;

import javax.enterprise.context.ApplicationScoped;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import org.acme.application.exceptions.UserNotFoundException;
import org.acme.application.model.User;
import org.acme.application.ports.in.LoginUserUseCase;
import org.acme.application.ports.out.AuthPort;
import org.acme.application.ports.out.GetUserPort;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
class LoginUserService implements LoginUserUseCase {

  private final AuthPort authService;

  private final GetUserPort getUserPort;

  public User login(String email, String password) throws FailedLoginException {
    User user = this.getUserPort.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
    String token;
    try {
      token = this.authService.login(email, password);
    } catch (LoginException e) {
      throw new FailedLoginException();
    }
    user.setToken(token);
    return user;
  }
}
