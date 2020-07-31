package org.acme.adapters.persistence.user;

import org.acme.adapters.persistence.article.ArticleJpaEntity;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Table(name = "user_e")
public class UserJpaEntity {

  @Id @GeneratedValue private Integer id;

  @Column(unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  @Column private String password;

  @Column private String bio;

  @Column private String image;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "author")
  public List<ArticleJpaEntity> articles;
}
