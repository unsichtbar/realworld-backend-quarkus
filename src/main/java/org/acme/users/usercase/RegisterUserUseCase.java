package org.acme.users.usercase;

import org.acme.exceptions.EmailAlreadyTakenException;
import org.acme.exceptions.UsernameAlreadyTakenException;
import org.acme.models.User;

import lombok.NonNull;

public interface RegisterUserUseCase {

    User registerUser(UserRegistrationCommand registrant)
            throws UsernameAlreadyTakenException, EmailAlreadyTakenException;
  
    class UserRegistrationCommand {
      @NonNull private String username;
      @NonNull private String email;
      @NonNull private String password;
  
      public UserRegistrationCommand(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
      }
    }
  }