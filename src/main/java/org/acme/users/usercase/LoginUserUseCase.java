package org.acme.users.usercase;

import org.acme.exceptions.LoginException;
import org.acme.models.User;

public interface LoginUserUseCase {
    public User login(String email, String password) throws LoginException;
  }