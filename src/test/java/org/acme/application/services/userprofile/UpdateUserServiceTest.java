package org.acme.application.services.userprofile;

import static org.junit.jupiter.api.Assertions.*;

import org.acme.application.exceptions.EmailAreadyTakenException;
import org.acme.application.exceptions.UserNotFoundException;
import org.acme.application.exceptions.UsernameAlreadyTakenException;
import org.acme.application.model.User;
import org.acme.application.ports.in.UpdateUserUseCase;
import org.acme.application.ports.out.AuthPort;
import org.acme.application.ports.out.GetUserPort;
import org.acme.application.ports.out.UpdateUserPort;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateUserServiceTest {
  @InjectMocks UpdateUserService sut;
  @Mock GetUserPort getUserPort;
  @Mock AuthPort authService;
  @Mock UpdateUserPort updateUserPort;

  @Test
  @DisplayName("Passing nothing does not create anything")
  void passing_nothing_does_nothing() {
    // Arrange

    // Act
    Assertions.assertThrows(UserNotFoundException.class, () -> sut.updateUser(null));
    // Assert
  }

  @Test
  @DisplayName("The user being updated must exist")
  void user_must_exist_to_be_updated() {
    // Arrange
    UpdateUserUseCase.UpdateUserCommand payload =
        UpdateUserUseCase.UpdateUserCommand.builder().user(1234).build();
    Mockito.when(this.getUserPort.getUserById(1234)).thenReturn(Optional.empty());
    // Act
    Assertions.assertThrows(UserNotFoundException.class, () -> sut.updateUser(payload));
    // Assert
  }

  @Test
  @DisplayName("The username can be updated")
  void username_is_updated() {
    // Arrange
    Integer id = 1234;
    String newName = "newName";
    UpdateUserUseCase.UpdateUserCommand payload =
        UpdateUserUseCase.UpdateUserCommand.builder().user(id).username(newName).build();

    User existing = User.builder().username("old name").id(id).build();
    Mockito.when(this.getUserPort.getUserById(id)).thenReturn(Optional.of(existing));
    Mockito.when(this.getUserPort.getUserByUsername(newName)).thenReturn(Optional.empty());
    Mockito.when(this.updateUserPort.save(Mockito.any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
    // Act
    User actual = sut.updateUser(payload);
    // Assert
    Assertions.assertEquals(newName, actual.getUsername());
  }

  @Test
  @DisplayName("The email can be updated")
  void email_is_updated() {
    // Arrange
    Integer id = 1234;
    String newEmail = "new@email.com";
    UpdateUserUseCase.UpdateUserCommand payload =
        UpdateUserUseCase.UpdateUserCommand.builder().user(id).email(newEmail).build();

    User existing = User.builder().email("old@email.com").id(id).build();
    Mockito.when(this.getUserPort.getUserById(id)).thenReturn(Optional.of(existing));
    Mockito.when(this.getUserPort.getUserByEmail(newEmail)).thenReturn(Optional.empty());
    Mockito.when(this.updateUserPort.save(Mockito.any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
    // Act
    User actual = sut.updateUser(payload);
    // Assert
    Assertions.assertEquals(newEmail, actual.getEmail());
  }

  @Test
  @DisplayName("The password can be updated")
  void password_is_updated() {
    // Arrange
    Integer id = 1234;
    String newPassword = "new@email.com";
    UpdateUserUseCase.UpdateUserCommand payload =
        UpdateUserUseCase.UpdateUserCommand.builder().user(id).password(newPassword).build();

    User existing = User.builder().email("old@email.com").id(id).build();
    Mockito.when(this.getUserPort.getUserById(id)).thenReturn(Optional.of(existing));
    Mockito.when(this.authService.encrypt(newPassword)).thenReturn("safe_encrypted_password");
    Mockito.when(this.updateUserPort.save(Mockito.any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
    // Act
    User actual = sut.updateUser(payload);
    // Assert
    Assertions.assertEquals("safe_encrypted_password", actual.getPassword());
  }

  @Test
  @DisplayName("The image can be updated")
  void image_is_updated() {
    // Arrange
    Integer id = 1234;
    String newImage = "new image";
    UpdateUserUseCase.UpdateUserCommand payload =
        UpdateUserUseCase.UpdateUserCommand.builder().user(id).image(newImage).build();

    User existing = User.builder().image("old image").id(id).build();
    Mockito.when(this.getUserPort.getUserById(id)).thenReturn(Optional.of(existing));
    Mockito.when(this.updateUserPort.save(Mockito.any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
    // Act
    User actual = sut.updateUser(payload);
    // Assert
    Assertions.assertEquals(newImage, actual.getImage());
  }

  @Test
  @DisplayName("The bio can be updated")
  void bio_is_updated() {
    // Arrange
    Integer id = 1234;
    String newBio = "new bio";
    UpdateUserUseCase.UpdateUserCommand payload =
        UpdateUserUseCase.UpdateUserCommand.builder().user(id).bio(newBio).build();

    User existing = User.builder().email("old@email.com").id(id).build();
    Mockito.when(this.getUserPort.getUserById(id)).thenReturn(Optional.of(existing));
    Mockito.when(this.updateUserPort.save(Mockito.any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
    // Act
    User actual = sut.updateUser(payload);
    // Assert
    Assertions.assertEquals(newBio, actual.getBio());
  }

  @Test
  @DisplayName("User cannot update username if that username is already taken")
  void cannot_change_username_if_username_already_taken() {
    // Arrange
    Integer id = 1234;
    UpdateUserUseCase.UpdateUserCommand payload =
        UpdateUserUseCase.UpdateUserCommand.builder().user(id).username("colliding").build();
    Mockito.when(this.getUserPort.getUserById(id)).thenReturn(Optional.of(User.builder().build()));
    Mockito.when(this.getUserPort.getUserByUsername("colliding"))
        .thenReturn(Optional.of(User.builder().build()));
    // Act
    Assertions.assertThrows(UsernameAlreadyTakenException.class, () -> sut.updateUser(payload));
    // Assert
  }

  @Test
  @DisplayName("User cannot update email if that email is already taken")
  void cannot_change_email_if_email_already_taken() {
    // Arrange
    Integer id = 1234;
    UpdateUserUseCase.UpdateUserCommand payload =
        UpdateUserUseCase.UpdateUserCommand.builder().user(id).email("colliding").build();
    Mockito.when(this.getUserPort.getUserById(id)).thenReturn(Optional.of(User.builder().build()));
    Mockito.when(this.getUserPort.getUserByEmail("colliding"))
        .thenReturn(Optional.of(User.builder().build()));
    // Act
    Assertions.assertThrows(EmailAreadyTakenException.class, () -> sut.updateUser(payload));
    // Assert
  }
}
