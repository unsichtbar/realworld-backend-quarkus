package org.acme.application.services.userprofile;

import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.acme.application.exceptions.EmailAreadyTakenException;
import org.acme.application.exceptions.UsernameAlreadyTakenException;
import org.acme.application.model.User;
import org.acme.application.ports.in.RegisterUserUseCase;
import org.acme.application.ports.out.AuthPort;
import org.acme.application.ports.out.GetUserPort;
import org.acme.application.ports.out.SaveUserPort;

@ApplicationScoped
@AllArgsConstructor
class RegisterUserService implements RegisterUserUseCase {

  private final AuthPort authService;
  private final GetUserPort getUserPort;
  private final SaveUserPort saveUserPort;

  public User registerUser(UserRegistrationCommand registrant) {
    this.getUserPort
        .getUserByUsername(registrant.getUsername())
        .ifPresent(
            (existing) -> {
              throw new UsernameAlreadyTakenException();
            });
    this.getUserPort
        .getUserByEmail(registrant.getEmail())
        .ifPresent(
            (existing) -> {
              throw new EmailAreadyTakenException();
            });
    User user =
        User.builder()
            .username(registrant.getUsername())
            .email(registrant.getEmail())
            .password(authService.encrypt(registrant.getPassword()))
            .build();
    user = this.saveUserPort.saveUser(user);
    String token = this.authService.generateToken(user);
    user.setToken(token);
    return user;
  }
}
