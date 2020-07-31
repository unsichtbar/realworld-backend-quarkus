package org.acme.application.ports.in;

import org.acme.application.model.Profile;
import org.acme.application.model.User;
import java.util.Optional;

public interface GetProfileQuery {
  Profile getProfile(String username, Optional<User> request);
}
