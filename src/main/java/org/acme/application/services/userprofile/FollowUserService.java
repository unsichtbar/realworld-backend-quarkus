package org.acme.application.services.userprofile;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.acme.application.model.Profile;
import org.acme.application.model.User;
import org.acme.application.ports.in.FollowUserUseCase;
import org.acme.application.ports.in.GetProfileQuery;
import org.acme.application.ports.out.SaveFollowRelationPort;

@ApplicationScoped
@AllArgsConstructor
class FollowUserService implements FollowUserUseCase {
  private final SaveFollowRelationPort followUserPort;
  private final GetProfileQuery getProfileQuery;

  @Override
  public Profile follow(User followed, User follower) {
    Profile followedProfile =
        getProfileQuery.getProfile(followed.getUsername(), Optional.of(follower));
    if (followedProfile.getFollowing()) {
      return followedProfile;
    }
    this.followUserPort.saveFollowRelation(followed, follower);
    followedProfile.setFollowing(true);
    return followedProfile;
  }

  @Override
  public Profile unfollow(User followed, User follower) {
    Profile followedProfile =
        getProfileQuery.getProfile(followed.getUsername(), Optional.of(follower));
    if (!followedProfile.getFollowing()) {
      return followedProfile;
    }
    this.followUserPort.removeFollowRelation(followed, follower);
    followedProfile.setFollowing(false);
    return followedProfile;
  }
}
