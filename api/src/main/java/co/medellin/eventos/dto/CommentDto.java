package co.medellin.eventos.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long authorId;
    private Long eventId;
}
