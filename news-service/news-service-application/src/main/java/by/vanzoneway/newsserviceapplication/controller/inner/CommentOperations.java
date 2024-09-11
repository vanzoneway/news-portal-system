package by.vanzoneway.newsserviceapplication.controller.inner;


import by.vanzoneway.newsserviceapi.dto.CommentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/default")
public interface CommentOperations {


    @GetMapping("/{commentId}")
    CommentDto getCommentById(@PathVariable Long commentId);

    @DeleteMapping("/{commentId}")
    ResponseEntity<String> deleteCommentById(@PathVariable Long commentId);



}
