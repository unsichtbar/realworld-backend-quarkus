package org.acme.application.ports.in;

import org.acme.application.exceptions.EmailAreadyTakenException;
import org.acme.application.exceptions.UsernameAlreadyTakenException;
import org.acme.application.model.SelfValidating;
import org.acme.application.model.User;
import javax.validation.constraints.NotNull;
import lombok.Value;

public interface RegisterUserUseCase {

  User registerUser(UserRegistrationCommand registrant)
      throws UsernameAlreadyTakenException, EmailAreadyTakenException;

  @Value
  class UserRegistrationCommand extends SelfValidating<UserRegistrationCommand> {
    @NotNull private String username;
    @NotNull private String email;
    @NotNull private String password;

    public UserRegistrationCommand(String username, String email, String password) {
      this.username = username;
      this.email = email;
      this.password = password;
      this.validateSelf();
    }
  }
}
