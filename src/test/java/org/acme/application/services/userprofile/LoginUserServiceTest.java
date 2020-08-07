package org.acme.application.services.userprofile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import org.acme.application.exceptions.UserNotFoundException;
import org.acme.application.model.User;
import org.acme.application.ports.out.AuthPort;
import org.acme.application.ports.out.GetUserPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoginUserServiceTest {

  @InjectMocks LoginUserService sut;
  @Mock AuthPort authPort;
  @Mock GetUserPort getUserPort;

  @DisplayName("logging in generates a new token")
  @Test
  void generate_new_token_on_login() throws LoginException {

    // Arrange
    String email = "hello@world.com";
    String password = "abc123";
    String token = "123456789";
    when(this.authPort.login(email, password)).thenReturn(token);
    Optional<User> user = Optional.of(User.builder().email(email).password(password).build());
    when(this.getUserPort.getUserByEmail(email)).thenReturn(user);
    // Act

    User actual = Assertions.assertDoesNotThrow(() -> this.sut.login(email, password));

    // Assert
    Assertions.assertEquals(token, actual.getToken());
  }

  @DisplayName("throws error if user does not exist")
  @Test
  void error_if_no_user() {
    // Arrange
    String email = "hello@world.com";
    String password = "abc123";
    when(this.getUserPort.getUserByEmail(email)).thenThrow(UserNotFoundException.class);
    // Act & Assert
    Assertions.assertThrows(UserNotFoundException.class, () -> this.sut.login(email, password));
  }

  @DisplayName("throw failed login exception if unable to login")
  @Test
  void error_if_login_doesnt_work() throws LoginException {
    // Arrange
    String email = "hello@world";
    String password = "abc123";
    Optional<User> user = Optional.of(User.builder().build());
    when(this.getUserPort.getUserByEmail(email)).thenReturn(user);
    when(this.authPort.login(email, password)).thenThrow(LoginException.class);
    // Act & Assert
    Assertions.assertThrows(FailedLoginException.class, () -> this.sut.login(email, password));
  }
}
