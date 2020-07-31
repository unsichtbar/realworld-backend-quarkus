package org.acme.users.services;

import javax.enterprise.context.ApplicationScoped;

import org.acme.auth.AuthPort;
import org.acme.exceptions.LoginException;
import org.acme.exceptions.UserNotFoundException;
import org.acme.models.User;
import org.acme.users.UserRepository;
import org.acme.users.usercase.LoginUserUseCase;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
class LoginUserService implements LoginUserUseCase {

  private final AuthPort authService;

  private final UserRepository getUserPort;

  public User login(String email, String password) throws LoginException {
    User user = this.getUserPort.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
    String token;
    try {
      token = this.authService.login(email, password);
    } catch (LoginException e) {
      throw new LoginException();
    }
    user.setToken(token);
    return user;
  }
}