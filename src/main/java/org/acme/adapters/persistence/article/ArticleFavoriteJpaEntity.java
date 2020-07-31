package org.acme.adapters.persistence.article;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
class ArticleFavoriteJpaEntity {

  @Id @GeneratedValue private Integer id;
  @Column private Integer userId;
  @Column private Integer articleId;
}
