package org.acme.adapters.persistence.article;

import org.acme.adapters.persistence.Audit;
import org.acme.adapters.persistence.tag.TagJpaEntity;
import org.acme.adapters.persistence.user.UserJpaEntity;
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
public class ArticleJpaEntity extends Audit {
  @Id @GeneratedValue private Integer id;

  @Column(unique = true)
  private String slug;

  @Column(unique = true)
  private String title;

  @Column private String description;
  @Column private String body;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "article_tag",
      joinColumns = @JoinColumn(name = "article_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private List<TagJpaEntity> tags;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private UserJpaEntity author;

  public void removeTag(TagJpaEntity tag) {
    this.tags.remove(tag);
    tag.getArticles().remove(this);
  }
}
