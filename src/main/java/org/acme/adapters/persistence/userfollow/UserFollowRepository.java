package org.acme.adapters.persistence.userfollow;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowRepository extends JpaRepository<UserFollowJpaEntity, Integer> {


  Boolean existsByFollowerAndFollowed(Integer follower, Integer followed);

  UserFollowJpaEntity findByFollowerAndFollowed(Integer follower, Integer followed);
}
