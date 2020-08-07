package org.acme.adapters.persistence.comment;

import javax.persistence.*;
import lombok.Data;
import org.acme.adapters.persistence.Audit;
import org.acme.adapters.persistence.article.ArticleJpaEntity;

@Entity
@Data
public class CommentJpaEntity extends Audit {

  @Id @GeneratedValue private Integer id;
  @Column private String body;

  @Column private Integer authorId;

  @JoinColumn @ManyToOne private ArticleJpaEntity article;
}
