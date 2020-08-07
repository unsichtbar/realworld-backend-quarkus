package org.acme.application.services.userprofile;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.acme.application.exceptions.EmailAreadyTakenException;
import org.acme.application.exceptions.UserNotFoundException;
import org.acme.application.exceptions.UsernameAlreadyTakenException;
import org.acme.application.model.User;
import org.acme.application.ports.in.UpdateUserUseCase;
import org.acme.application.ports.out.AuthPort;
import org.acme.application.ports.out.GetUserPort;
import org.acme.application.ports.out.UpdateUserPort;

@AllArgsConstructor
@ApplicationScoped
@Transactional
class UpdateUserService implements UpdateUserUseCase {

  private final GetUserPort getUserPort;
  private final AuthPort authService;
  private final UpdateUserPort updateUserPort;

  public enum SearchType {
    USERNAME,
    EMAIL
  }

  private Optional<User> getUser(String value, SearchType searchType) {
    switch (searchType) {
      case USERNAME:
        return getUserPort.getUserByUsername(value);
      case EMAIL:
        return getUserPort.getUserByEmail(value);
      default:
        throw new IllegalArgumentException();
    }
  }

  public User updateUser(UpdateUserCommand updatePayload) {
    if (updatePayload == null) {
      throw new UserNotFoundException();
    }
    User existing =
        getUserPort.getUserById(updatePayload.getUser()).orElseThrow(UserNotFoundException::new);

    if (updatePayload.getUsername() != null && !updatePayload.getUsername().isBlank()) {

      Optional<User> collidingUsername = getUser(updatePayload.getUsername(), SearchType.USERNAME);
      if (collidingUsername.isPresent()) {
        throw new UsernameAlreadyTakenException();
      }
      existing.setUsername(updatePayload.getUsername());
    }
    if (updatePayload.getEmail() != null && isValidEmail(updatePayload.getEmail())) {
      Optional<User> collingEmail = getUser(updatePayload.getEmail(), SearchType.EMAIL);
      if (collingEmail.isPresent()) {
        throw new EmailAreadyTakenException();
      }
      existing.setEmail(updatePayload.getEmail());
    }
    if (updatePayload.getPassword() != null && !updatePayload.getPassword().isBlank()) {
      existing.setPassword(authService.encrypt(updatePayload.getPassword()));
    }
    if (updatePayload.getImage() != null && !"".equals(updatePayload.getImage())) {
      existing.setImage(updatePayload.getImage());
    }
    if (updatePayload.getBio() != null) {
      existing.setBio(updatePayload.getBio());
    }

    return this.updateUserPort.save(existing);
  }

  private Boolean isValidEmail(String email) {
    return true; // TODO implement later
  }
}
