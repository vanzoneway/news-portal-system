package by.vanzoneway.newsserviceapplication.controller.inner.impl;

import by.vanzoneway.newsserviceapi.dto.CommentDto;
import by.vanzoneway.newsserviceapplication.controller.inner.CommentOperations;
import by.vanzoneway.newsservicecore.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
public class CommentController implements CommentOperations {

    private final CommentService commentService;


    @Override
    public CommentDto getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @Override
    public ResponseEntity<String> deleteCommentById(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.deleteCommentById(commentId));
    }

//    @Override
//    public ResponseEntity<CommentDto> updateCommentById(@PathVariable Long commentId, @RequestBody @Valid CommentDto commentDto) {
//        return ResponseEntity.ok(commentService.updateCommentById(commentId, commentDto));
//    }
}
