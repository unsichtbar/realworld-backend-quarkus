package org.acme.adapters.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.security.auth.login.LoginException;
import org.acme.application.model.User;
import org.acme.application.ports.out.AuthPort;

@ApplicationScoped
class AuthAdapter implements AuthPort {

  @Override
  public String login(String email, String password) throws LoginException {
    // TODO Auto-generated method stub
    return "ABC123";
  }

  @Override
  public String encrypt(String password) {
    // TODO Auto-generated method stub
    return "ABC123";
  }

  @Override
  public String generateToken(User user) {
    // TODO Auto-generated method stub
    return "ABC123";
  }
}
