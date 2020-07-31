package org.acme.application.ports.in;

import org.acme.application.exceptions.EmailAreadyTakenException;
import org.acme.application.exceptions.UsernameAlreadyTakenException;
import org.acme.application.model.User;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

public interface RegisterUserUseCase {

  User registerUser(UserRegistrationCommand registrant)
      throws UsernameAlreadyTakenException, EmailAreadyTakenException;

  @Value
  @AllArgsConstructor
  class UserRegistrationCommand  {
    @NonNull private String username;
    @NonNull private String email;
    @NonNull private String password;
  }
}
