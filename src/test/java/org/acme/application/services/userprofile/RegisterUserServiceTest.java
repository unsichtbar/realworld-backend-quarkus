package org.acme.application.services.userprofile;

import org.acme.application.exceptions.EmailAreadyTakenException;
import org.acme.application.exceptions.UsernameAlreadyTakenException;
import org.acme.application.model.User;
import org.acme.application.ports.in.RegisterUserUseCase;
import org.acme.application.ports.out.AuthPort;
import org.acme.application.ports.out.GetUserPort;
import org.acme.application.ports.out.SaveUserPort;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {

  @InjectMocks RegisterUserService sut;
  @Mock GetUserPort getUserPort;
  @Mock AuthPort authService;
  @Mock SaveUserPort saveUserPort;

  @Test
  @DisplayName("a user is registered")
  void register_user() {
    // Arrange
    String username = "bob";
    String email = "hello@world.com";
    String password = "password";
    String encrypted_password = "encrypted_password";
    Integer id = 1234;
    String token = "abc123";
    User user = User.builder().password(encrypted_password).email(email).username(username).build();
    User userWithId =
        User.builder().password(encrypted_password).email(email).username(username).id(id).build();
    RegisterUserUseCase.UserRegistrationCommand registrant =
        new RegisterUserUseCase.UserRegistrationCommand(username, email, password);
    Mockito.when(getUserPort.getUserByUsername(username)).thenReturn(Optional.empty());
    Mockito.when(getUserPort.getUserByEmail(email)).thenReturn(Optional.empty());
    Mockito.when(authService.encrypt(password)).thenReturn(encrypted_password);
    Mockito.when(saveUserPort.saveUser(user)).thenReturn(userWithId);
    Mockito.when(authService.generateToken(userWithId)).thenReturn(token);
    // Act
    User actual = sut.registerUser(registrant);
    // Assert

    User expected =
        User.builder()
            .password(encrypted_password)
            .email(email)
            .username(username)
            .id(id)
            .token(token)
            .build();

    Assertions.assertEquals(expected, actual);
  }

  @Test
  @DisplayName(
      "An existing user is found with the desired username to the new user is not registered")
  void user_with_desired_username_already_exists_so_throws_exception() {
    // Arrange
    String username = "bob";
    String email = "hello@world.com";
    String password = "password";
    String encrypted_password = "encrypted_password";
    Integer id = 1234;

    User existingUser =
        User.builder().password(encrypted_password).email(email).username(username).id(id).build();
    Mockito.when(getUserPort.getUserByUsername(username)).thenReturn(Optional.of(existingUser));
    // Act & Assert
    Assertions.assertThrows(
        UsernameAlreadyTakenException.class,
        () ->
            sut.registerUser(
                new RegisterUserUseCase.UserRegistrationCommand(username, email, password)));
    Mockito.verifyNoInteractions(this.saveUserPort);
  }

  @Test
  @DisplayName("An existing user is found with the desired email so the new user is not registered")
  void existing_user_found_with_desired_email() {
    // Arrange
    String username = "bob";
    String email = "hello@world.com";
    String password = "password";
    String encrypted_password = "encrypted_password";
    Integer id = 1234;
    User foundUser =
        User.builder().password(encrypted_password).email(email).username(username).id(id).build();
    Mockito.when(getUserPort.getUserByUsername(username)).thenReturn(Optional.empty());
    Mockito.when(getUserPort.getUserByEmail(email)).thenReturn(Optional.of(foundUser));
    // Act
    Assertions.assertThrows(
        EmailAreadyTakenException.class,
        () ->
            sut.registerUser(
                new RegisterUserUseCase.UserRegistrationCommand(username, email, password)));
    Mockito.verifyNoInteractions(this.saveUserPort);
  }

  @Test
  @DisplayName("Registration does not pass validation so user is not registered")
  void does_not_pass_valiation_so_user_is_not_registered() {
    // Arrange
    String username = "bob";
    String email = "hello@world.com";
    String password = null;
    // Act
    NullPointerException actual =
        Assertions.assertThrows(
            NullPointerException.class,
            () -> new RegisterUserUseCase.UserRegistrationCommand(username, email, password));
  }
}
