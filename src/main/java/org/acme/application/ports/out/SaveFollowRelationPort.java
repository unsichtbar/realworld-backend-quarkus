package org.acme.application.ports.out;

import org.acme.application.model.Profile;
import org.acme.application.model.User;

public interface SaveFollowRelationPort {
  Profile saveFollowRelation(User followed, User follower);

  Profile removeFollowRelation(User followed, User follower);
}
