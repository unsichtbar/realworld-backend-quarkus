package org.acme.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Comment {
    private CommentId id;
    private LocalDateTime createAt;
    private LocalDateTime updated;
    private String body;
    private Profile author;
    private Integer articleId;

    @Data
    @Builder
    public static class CommentId {
        private Integer id;
    }
}