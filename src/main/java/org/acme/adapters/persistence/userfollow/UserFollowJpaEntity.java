package org.acme.adapters.persistence.userfollow;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFollowJpaEntity {

  @Id @GeneratedValue @Column private Integer _id;

  @Column private Integer followed;
  @Column private Integer follower;
}
