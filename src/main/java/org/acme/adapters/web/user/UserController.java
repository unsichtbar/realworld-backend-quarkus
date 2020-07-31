package org.acme.adapters.web.user;

import javax.ws.rs.PUT;

import org.acme.application.model.User;
import org.acme.application.ports.in.UpdateUserUseCase;
import org.acme.application.ports.in.UpdateUserUseCase.UpdateUserCommand;

public class UserController {
    
  private UserWebMapper userMapper;
  private UpdateUserUseCase userUpdator;

    @PUT
    public GetUserResponse replace(
         UserUpdatePayload body) {
             User user = User.builder().id(1234).build();
      try {
        UpdateUserUseCase.UpdateUserCommand payload =
            UpdateUserCommand.builder()
                .bio(body.getUser().getBio())
                .email(body.getUser().getEmail())
                .image(body.getUser().getImage())
                .password(body.getUser().getPassword())
                .user(user.getId())
                .username(body.getUser().getUsername())
                .build();
        User mapped = this.userUpdator.updateUser(payload);
       
        return userMapper.mapUserToResponse(mapped);
      } catch (Exception e) {
        return null;
      }
    }
}