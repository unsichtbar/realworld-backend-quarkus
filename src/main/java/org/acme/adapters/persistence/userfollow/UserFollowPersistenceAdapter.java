package org.acme.adapters.persistence.userfollow;

import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.acme.adapters.persistence.user.UserJpaEntity;
import org.acme.adapters.persistence.user.UserRepository;
import org.acme.application.model.Profile;
import org.acme.application.model.User;
import org.acme.application.ports.out.SaveFollowRelationPort;

@ApplicationScoped
@AllArgsConstructor
// TODO all this code should be in (application) module
class UserFollowPersistenceAdapter implements SaveFollowRelationPort {

  private UserRepository userRepository;
  private UserFollowRepository followRepository;

  @Override
  public Profile saveFollowRelation(User followed, User follower) {
    UserJpaEntity followedEntity = userRepository.findByEmail(followed.getEmail());
    UserJpaEntity followerEntity = userRepository.findByEmail(follower.getEmail());
    if (followRepository.existsByFollowerAndFollowed(
        followerEntity.getId(), followedEntity.getId())) {
      return Profile.builder()
          .username(followed.getUsername())
          .following(true)
          .bio(followed.getBio())
          .image(followed.getImage())
          .build();
    }
    followRepository.save(
        UserFollowJpaEntity.builder()
            .follower(followerEntity.getId())
            .followed(followedEntity.getId())
            .build());
    return Profile.builder()
        .username(followed.getUsername())
        .following(true)
        .bio(followed.getBio())
        .image(followed.getImage())
        .build();
  }

  @Override
  public Profile removeFollowRelation(User followed, User follower) {
    UserJpaEntity followedEntity = userRepository.findByEmail(followed.getEmail());
    UserJpaEntity followerEntity = userRepository.findByEmail(follower.getEmail());
    if (!followRepository.existsByFollowerAndFollowed(
        followerEntity.getId(), followedEntity.getId())) {
      return Profile.builder()
          .username(followed.getUsername())
          .following(false)
          .bio(followed.getBio())
          .image(followed.getImage())
          .build();
    }
    followRepository.delete(
        followRepository.findByFollowerAndFollowed(followerEntity.getId(), followedEntity.getId()));
    return Profile.builder()
        .username(followed.getUsername())
        .following(false)
        .bio(followed.getBio())
        .image(followed.getImage())
        .build();
  }
}
