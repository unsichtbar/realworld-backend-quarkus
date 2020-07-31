package org.acme.application.ports.in;

import org.acme.application.model.Profile;
import org.acme.application.model.User;

public interface FollowUserUseCase {
  Profile follow(User followed, User follower);

  Profile unfollow(User followed, User follower);
}
