package org.acme.application.ports.in;

import java.util.Optional;
import org.acme.application.model.Profile;
import org.acme.application.model.User;

public interface GetProfileQuery {
  Profile getProfile(String username, Optional<User> request);
}
