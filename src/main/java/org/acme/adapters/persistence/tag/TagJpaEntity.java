package org.acme.adapters.persistence.tag;

import org.acme.adapters.persistence.article.ArticleJpaEntity;
import java.util.List;
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
public class TagJpaEntity {
  @Id @GeneratedValue private Integer id;

  @Column(unique = true)
  private String tag;

  @ManyToMany(mappedBy = "tags")
  private List<ArticleJpaEntity> articles;
}
