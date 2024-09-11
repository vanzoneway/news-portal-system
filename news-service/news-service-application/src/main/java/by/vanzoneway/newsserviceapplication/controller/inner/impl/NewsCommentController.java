package by.vanzoneway.newsserviceapplication.controller.inner.impl;


import by.vanzoneway.newsserviceapi.dto.CommentCreatedDto;
import by.vanzoneway.newsserviceapi.dto.CommentDto;
import by.vanzoneway.newsserviceapi.dto.NewsCommentDto;
import by.vanzoneway.newsserviceapplication.controller.inner.NewsCommentOperations;
import by.vanzoneway.newsservicecore.service.NewsCommentService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news/{newsId}/comments")
@AllArgsConstructor
public class NewsCommentController implements NewsCommentOperations {

    private final NewsCommentService newsCommentService;

    @Override
    public ResponseEntity<NewsCommentDto> getPageNewsWithComments(
            @PathVariable Long newsId,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer limit) {

        return ResponseEntity.ok(newsCommentService.getPageNewsWithComments(newsId, offset, limit));
    }

    @Override
    public ResponseEntity<CommentCreatedDto> createComment(@PathVariable Long newsId,
                                                           @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(newsCommentService.createComment(newsId, commentDto));
    }

    @Override
    public ResponseEntity<CommentDto> updateCommentByNewsIdAndCommentsId(Long newsId,
                                                                         Long commentsId,
                                                                         CommentDto commentDto) {
        return ResponseEntity.ok(newsCommentService.updateCommentByNewsIdAndCommentsId(newsId, commentsId, commentDto));
    }

    @Override
    public Page<CommentDto> searchComments(@PathVariable Long newsId,
                                           @RequestParam String query,
                                           @RequestParam @Min(0) Integer offset,
                                           @RequestParam @Min(1) @Max(100) Integer limit) {
        return newsCommentService.searchCommentsByNewsId(newsId, query, offset, limit);
    }
}
