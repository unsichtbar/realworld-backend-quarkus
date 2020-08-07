package org.acme.application.services.userprofile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.acme.application.model.Profile;
import org.acme.application.model.User;
import org.acme.application.ports.in.GetProfileQuery;
import org.acme.application.ports.out.SaveFollowRelationPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FollowUserServiceTest {

  @InjectMocks FollowUserService sut;
  @Mock SaveFollowRelationPort saveFollowRelationPort;
  @Mock GetProfileQuery getProfileQuery;

  @Test
  @DisplayName("a user can follow another user")
  void follow_another_user() {
    // Arrange
    User followed = User.builder().id(1234).username("bob").build();
    User follower = User.builder().id(5678).username("joe").build();

    Profile followedProfile = Profile.builder().username("bob").following(false).build();
    when(this.getProfileQuery.getProfile("bob", Optional.of(follower))).thenReturn(followedProfile);
    // Act
    Profile actual = sut.follow(followed, follower);
    // Assert
    Assertions.assertEquals(true, actual.getFollowing());
  }

  @Test
  @DisplayName("a user already followed is still followed")
  void follow_another_user_already_followed() {
    // Arrange
    User followed = User.builder().id(1234).username("bob").build();
    User follower = User.builder().id(5678).username("joe").build();

    Profile followedProfile = Profile.builder().username("bob").following(true).build();
    when(this.getProfileQuery.getProfile("bob", Optional.of(follower))).thenReturn(followedProfile);
    // Act
    Profile actual = sut.follow(followed, follower);
    // Assert
    Assertions.assertEquals(true, actual.getFollowing());
  }

  @Test
  @DisplayName("a user can unfollow another user")
  void unfollow_another_user() {
    // Arrange
    User followed = User.builder().id(1234).username("bob").build();
    User follower = User.builder().id(5678).username("joe").build();

    Profile followedProfile = Profile.builder().username("bob").following(true).build();
    when(this.getProfileQuery.getProfile("bob", Optional.of(follower))).thenReturn(followedProfile);
    // Act
    Profile actual = sut.unfollow(followed, follower);
    // Assert
    Assertions.assertEquals(false, actual.getFollowing());
  }

  @Test
  @DisplayName("a user already unfollowed is still unfollowed")
  void unfollow_another_user_already_unfollowed() {
    // Arrange
    User followed = User.builder().id(1234).username("bob").build();
    User follower = User.builder().id(5678).username("joe").build();

    Profile followedProfile = Profile.builder().username("bob").following(false).build();
    when(this.getProfileQuery.getProfile("bob", Optional.of(follower))).thenReturn(followedProfile);
    // Act
    Profile actual = sut.unfollow(followed, follower);
    // Assert
    Assertions.assertEquals(false, actual.getFollowing());
  }
}
