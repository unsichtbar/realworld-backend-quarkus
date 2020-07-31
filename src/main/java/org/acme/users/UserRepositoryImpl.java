package org.acme.users;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.acme.models.User;

@ApplicationScoped
class UserRepositoryImpl implements UserRepository {


    @Override
    public Optional<User> getUserByUsername(String username) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserById(Integer userId) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }
    
}