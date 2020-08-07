package org.acme.application.ports.out;

import javax.security.auth.login.LoginException;
import org.acme.application.model.User;

public interface AuthPort {

  String login(String email, String password) throws LoginException;

  String encrypt(String password);

  String generateToken(User user);
}
