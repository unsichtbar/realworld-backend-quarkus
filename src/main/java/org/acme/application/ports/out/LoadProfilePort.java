package org.acme.application.ports.out;

import java.util.Optional;
import org.acme.application.model.Profile;
import org.acme.application.model.User;

public interface LoadProfilePort {
  Optional<Profile> loadProfile(String username);

  Boolean isFollowing(String username, Optional<User> follower);
}
