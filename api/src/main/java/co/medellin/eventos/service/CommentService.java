package co.medellin.eventos.service;

import co.medellin.eventos.dto.CommentDto;
import co.medellin.eventos.model.Comment;
import co.medellin.eventos.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setDeleted(comment.isDeleted());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setAuthorId(comment.getAuthor());
        dto.setEventId(comment.getEvent());

        return dto;
    }

    public List<CommentDto> getCommentsByEvent(Long eventId) {
        return commentRepository.findByEvent(eventId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto createComment(CommentDto dto) {
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setDeleted(false);
        comment.setCreatedAt(dto.getCreatedAt());
        comment.setUpdatedAt(dto.getUpdatedAt());
        comment.setAuthor(dto.getAuthorId());
        comment.setEvent(dto.getEventId());

        comment = commentRepository.save(comment);

        return toDto(comment);
    }

    @Transactional
    public CommentDto updateComment(Long id, CommentDto dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setContent(dto.getContent());
        comment.setUpdatedAt(LocalDateTime.now());
        comment = commentRepository.save(comment);

        return toDto(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setDeleted(true);
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }
}
