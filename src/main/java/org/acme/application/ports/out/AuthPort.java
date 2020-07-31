package org.acme.application.ports.out;

import org.acme.application.model.User;
import javax.security.auth.login.LoginException;

public interface AuthPort {

  String login(String email, String password) throws LoginException;

  String encrypt(String password);

  String generateToken(User user);
}
