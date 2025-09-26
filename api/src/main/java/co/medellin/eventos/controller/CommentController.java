package co.medellin.eventos.controller;

import co.medellin.eventos.dto.CommentDto;
import co.medellin.eventos.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/event/{eventId}")
    public List<CommentDto> getCommentsByEvent(@PathVariable Long eventId) {
        return commentService.getCommentsByEvent(eventId);
    }

    @PostMapping("/save")
    public CommentDto createComment(@RequestBody CommentDto dto) {
        return commentService.createComment(dto);
    }
    @PutMapping("/{id}")
    public CommentDto updateComment(@PathVariable Long id, @RequestBody CommentDto dto) {
        return commentService.updateComment(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
