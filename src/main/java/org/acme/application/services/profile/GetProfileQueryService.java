package org.acme.application.services.profile;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.acme.application.exceptions.UserNotFoundException;
import org.acme.application.model.Profile;
import org.acme.application.model.User;
import org.acme.application.ports.in.GetProfileQuery;
import org.acme.application.ports.out.LoadProfilePort;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class GetProfileQueryService implements GetProfileQuery {
  private LoadProfilePort profilePort;

  @Override
  public Profile getProfile(String username, Optional<User> request) {
    Boolean isFollowing = profilePort.isFollowing(username, request);
    Profile p = profilePort.loadProfile(username).orElseThrow(UserNotFoundException::new);
    p.setFollowing(isFollowing);
    return p;
  }
}