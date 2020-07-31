package org.acme.auth;

import org.acme.exceptions.LoginException;
import org.acme.models.User;

public interface AuthPort {

    String login(String email, String password) throws LoginException;
  
    String encrypt(String password);
  
    String generateToken(User user);
  }