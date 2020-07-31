package org.acme.application.ports.out;

import org.acme.application.model.Profile;
import org.acme.application.model.User;
import java.util.Optional;

public interface LoadProfilePort {
  Optional<Profile> loadProfile(String username);

  Boolean isFollowing(String username, Optional<User> follower);
}
