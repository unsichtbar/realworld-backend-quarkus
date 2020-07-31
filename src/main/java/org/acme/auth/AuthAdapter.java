package org.acme.auth;

import javax.enterprise.context.ApplicationScoped;

import org.acme.exceptions.LoginException;
import org.acme.models.User;

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