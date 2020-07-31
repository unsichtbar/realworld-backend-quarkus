package org.acme.users;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.exceptions.LoginException;
import org.acme.exceptions.UserNotFoundException;
import org.acme.models.User;
import org.acme.users.usercase.LoginUserUseCase;

import lombok.AllArgsConstructor;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class UsersController {

    private LoginUserUseCase service;

    @POST
    @Path("/login")
    public User login(
        @PathParam(value = "username") String username, @PathParam(value = "password") String password
    ){
        try {
        return service.login(username, password);
        } catch(LoginException e) {
            return null;
        } catch(UserNotFoundException e) {
            return null;
        }
    }



}