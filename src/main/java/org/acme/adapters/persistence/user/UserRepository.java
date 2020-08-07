package org.acme.adapters.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserJpaEntity, Integer> {

  UserJpaEntity findByUsername(String username);

  UserJpaEntity findByEmail(String email);
}
